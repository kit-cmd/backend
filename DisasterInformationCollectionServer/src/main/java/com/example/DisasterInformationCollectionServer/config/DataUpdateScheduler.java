package com.example.DisasterInformationCollectionServer.config;

import com.example.DisasterInformationCollectionServer.service.DisasterDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class DataUpdateScheduler {
    private final DisasterDataService disasterDataService;

    public DataUpdateScheduler(DisasterDataService disasterDataService) {
        this.disasterDataService = disasterDataService;
    }

    @Scheduled(fixedDelay = 60000) // 1분마다 실행
    public void updateData() throws IOException {
        disasterDataService.fetchData();
        log.info("1분마다 갱신 성공");
    }
}
