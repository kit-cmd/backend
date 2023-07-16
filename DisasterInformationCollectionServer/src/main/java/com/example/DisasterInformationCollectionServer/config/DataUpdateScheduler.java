package com.example.DisasterInformationCollectionServer.config;

import com.example.DisasterInformationCollectionServer.service.DisasterDataService;
import com.example.DisasterInformationCollectionServer.service.LocalDisasterDataService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@EnableScheduling
public class DataUpdateScheduler {
    private final DisasterDataService disasterDataService;
    private final LocalDisasterDataService localDisasterDataService;
    private final List<String> locationList;
    private final RedisTemplate<String, String> redisTemplate;

    public DataUpdateScheduler(DisasterDataService disasterDataService, LocalDisasterDataService localDisasterDataService, RedisTemplate<String, String> redisTemplate) {
        this.disasterDataService = disasterDataService;
        this.localDisasterDataService = localDisasterDataService;
        this.locationList = localDisasterDataService.getLocationName(); // CSV에서 지역 목록을 가져옴
        this.redisTemplate = redisTemplate;
    }

    @Scheduled(fixedDelay = 60000) // 3분마다 실행
    public void updateData() {
        try {
            String previousData = disasterDataService.getDisasterDataFromRedis(); // 이전 데이터 가져오기
            disasterDataService.fetchData(); // 새로운 데이터 가져오기
            log.info("이전 데이터: {}", previousData);
            // 변경된 부분 추출
            String currentData = disasterDataService.getDisasterDataFromRedis();
            log.info("변경된 데이터: {}", currentData);

            if (!previousData.equals(currentData)){
                redisTemplate.convertAndSend("change", currentData);
                log.info("새로운 메세지 전송");
            }

            log.info("스케줄러 호출 성공");
        } catch (Exception e) {
            log.info("스케줄러 호출 중 예외 발생", e);
        }
    }

//    @Scheduled(fixedDelay = 180000) // 3분마다 실행
//    public void localUpdateData() throws IOException {
//        for (String location : locationList) {
//                localDisasterDataService.fetchLocalDataForLocation(location); // 각 지역별로 데이터를 저장
//        }
//        log.info("지역별 데이터 스케쥴러 호출 성공");
//    }
}