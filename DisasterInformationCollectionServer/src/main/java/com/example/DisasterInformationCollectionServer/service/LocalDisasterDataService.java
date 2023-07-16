package com.example.DisasterInformationCollectionServer.service;

import com.example.DisasterInformationCollectionServer.config.ByteArrayRedisSerializer;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@Service
@Slf4j
public class LocalDisasterDataService {
    private static final String API_KEY = "yrTK4nSm8FqY3ZCD5PPkhfn%2FjG9BMYL3CjI3PbCUmx6kUaIpaC6Pnt9a0QdCQ9ogBcLwP8FukYm%2BqDt27It3ig%3D%3D";
    private static final String API_URL = "https://apis.data.go.kr/1741000/DisasterMsg4/getDisasterMsg2List";
    private static final String REDIS_KEY_PREFIX = "disasterData";

    private final RedisTemplate<String, byte[]> redisTemplate;

    public LocalDisasterDataService(RedisTemplate<String, byte[]> redisTemplate) {
        this.redisTemplate = redisTemplate;
        redisTemplate.setValueSerializer(new ByteArrayRedisSerializer());
        redisTemplate.setHashValueSerializer(new ByteArrayRedisSerializer());
        redisTemplate.afterPropertiesSet();
    }

    public List<String> getLocationName() {
        List<String> locationList = new ArrayList<>();
        String csvFilePath = "/csv/apicode.csv";

        try (InputStream inputStream = getClass().getResourceAsStream(csvFilePath);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

            log.info("Reading CSV file: {}", csvFilePath);

            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                String csvLocation = nextLine[2];
                locationList.add(csvLocation);
            }

            log.info("Successfully read {} location(s) from the CSV file.", locationList.size());
        } catch (IOException | CsvValidationException e) {
            log.error("Failed to read CSV file: {}", e.getMessage());
        }

        return locationList;
    }

    public String getLocalDisasterDataFromRedis(String location) {
        String key = REDIS_KEY_PREFIX + location; // Redis에서 사용하는 키(Key)
        byte[] dataBytes = (byte[]) redisTemplate.opsForHash().get(key, "data"); // Redis에서 데이터 조회

        if (dataBytes != null) {
            String data = new String(dataBytes, StandardCharsets.UTF_8);
            return data;
        }

        return null;
    }

    public void fetchLocalDataForLocation(String location) throws IOException {
        String todayDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd 00:00:00"));
        StringBuilder urlBuilder = new StringBuilder(API_URL);
        urlBuilder.append("?ServiceKey=" + API_KEY);
        urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("create_date", "UTF-8") + "=" + todayDate.replace(" ", "%20"));
        urlBuilder.append("&" + URLEncoder.encode("location_name", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8"));

        String urlString = urlBuilder.toString();
        URL url = new URL(urlString);
        log.info(String.valueOf(url));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "*/*;q=0.9");


        StringBuilder sb = new StringBuilder();

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("에러 발생: {}", e.getMessage());
        } finally {
            conn.disconnect();
        }

        String responseData = sb.toString();
        byte[] dataBytes = responseData.getBytes(StandardCharsets.UTF_8);
        redisTemplate.opsForHash().put(location, "data", dataBytes);
        log.info("Redis에 {} 데이터 저장: {}", location, dataBytes);
    }
}
