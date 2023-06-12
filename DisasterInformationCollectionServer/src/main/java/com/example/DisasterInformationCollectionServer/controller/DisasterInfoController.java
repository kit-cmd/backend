package com.example.DisasterInformationCollectionServer.controller;

import com.example.DisasterInformationCollectionServer.service.DisasterDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/disaster")
@Slf4j
public class DisasterInfoController {
    @Autowired
    private final DisasterDataService disasterDataService;

    public DisasterInfoController(DisasterDataService disasterDataService) {
        this.disasterDataService = disasterDataService;
    }

    @GetMapping("/fetch")
    public void fetchData() {
        try {
            disasterDataService.fetchData();
            log.info("재난 문자 api 호출 성공");
        } catch (IOException e) {
            // 예외 처리
            log.info("재난 문자 api 호출");
        }
    }

    @GetMapping("/info")
    public String getDisasterInfo() {
        // Redis에서 저장된 데이터를 조회하고 필요한 처리를 수행하여 반환하는 로직 작성
        String disasterData = disasterDataService.getDisasterDataFromRedis();
        // 필요한 데이터 가공 또는 처리 로직 추가
        return disasterData;
    }
}
