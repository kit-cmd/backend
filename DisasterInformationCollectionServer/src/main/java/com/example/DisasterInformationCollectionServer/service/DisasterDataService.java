package com.example.DisasterInformationCollectionServer.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class DisasterDataService {
    private static final String API_KEY = "yrTK4nSm8FqY3ZCD5PPkhfn%2FjG9BMYL3CjI3PbCUmx6kUaIpaC6Pnt9a0QdCQ9ogBcLwP8FukYm%2BqDt27It3ig%3D%3D";
    private static final String API_URL = "http://apis.data.go.kr/1741000/DisasterMsg3/getDisasterMsg1List";
    private static final String REDIS_KEY = "disasterData";


    private final RedisTemplate<String, String> redisTemplate;

    public DisasterDataService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getDisasterDataFromRedis() {
        String key = REDIS_KEY; // Redis에서 사용하는 키(Key)
        Object data = redisTemplate.opsForValue().get(key); // Redis에서 데이터 조회

        return String.valueOf(data);
    }

    public void fetchData() throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + API_KEY);
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        StringBuilder sb = new StringBuilder();

        BufferedReader rd;
        try {
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            conn.disconnect();
        }

        String responseData = sb.toString();
        redisTemplate.opsForValue().set(REDIS_KEY, responseData);
    }
}
