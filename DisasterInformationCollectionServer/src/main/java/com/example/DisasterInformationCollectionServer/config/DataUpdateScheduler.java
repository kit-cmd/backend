package com.example.DisasterInformationCollectionServer.config;

import com.example.DisasterInformationCollectionServer.service.DisasterDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@EnableScheduling
public class DataUpdateScheduler {
    private final DisasterDataService disasterDataService;

    public DataUpdateScheduler(DisasterDataService disasterDataService) {
        this.disasterDataService = disasterDataService;
    }

    @Scheduled(fixedDelay = 180000) // 3분마다 실행
    public void updateData() {
        try {
            disasterDataService.fetchData();
            log.info("스케쥴러 호출 성공");
        } catch (IOException e) {
            log.info("스케쥴러 호출 중 예외 발생", e);
        }
    }
}
