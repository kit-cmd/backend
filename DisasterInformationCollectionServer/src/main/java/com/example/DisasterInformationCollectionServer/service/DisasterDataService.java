package com.example.DisasterInformationCollectionServer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class DisasterDataService {
    private final static String API_KEY = "yrTK4nSm8FqY3ZCD5PPkhfn%2FjG9BMYL3CjI3PbCUmx6kUaIpaC6Pnt9a0QdCQ9ogBcLwP8FukYm%2BqDt27It3ig%3D%3D";
    private final static String API_URL = "http://apis.data.go.kr/1741000/DisasterMsg3/getDisasterMsg1List";
    private final static String REDIS_KEY = "disasterData";


    private final RedisTemplate<String, String> redisTemplate;

    public DisasterDataService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getDisasterDataFromRedis() {
        String key = REDIS_KEY; // Redis에서 사용하는 키(Key)
        Object data = redisTemplate.opsForValue().get(key); // Redis에서 데이터 조회
        log.info("정상적으로 redis 조회: {}", data);
        return String.valueOf(data);
    }

    // 테스트 코드용
    public String getValueFromRedis(String key) {
        // 실제 Redis 데이터 조회 로직
        Object data = redisTemplate.opsForValue().get(key);
        log.info("정상적으로 redis 조회: {}", data);
        return String.valueOf(data);
    }

    public void fetchData() throws IOException {
        String urlBuilder = API_URL + "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + API_KEY +
                "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8);

        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        StringBuilder sb = new StringBuilder();

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.info("에러 발생: {}", e);
        } finally {
            conn.disconnect();
        }

        String responseData = sb.toString();
        redisTemplate.opsForValue().set(REDIS_KEY, responseData);
        log.info("redis에 저장됨:{}", responseData);
    }
}
