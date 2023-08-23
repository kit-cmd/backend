package com.example.DisasterInformationCollectionServer.controller;

import com.example.DisasterInformationCollectionServer.service.DisasterDataService;
import com.example.DisasterInformationCollectionServer.service.LocalDisasterDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/disaster")
@Slf4j
public class DisasterInfoController {
    @Autowired
    private final DisasterDataService disasterDataService;
    @Autowired
    private final LocalDisasterDataService localDisasterDataService;

    public DisasterInfoController(DisasterDataService disasterDataService, LocalDisasterDataService localDisasterDataService) {
        this.disasterDataService = disasterDataService;
        this.localDisasterDataService = localDisasterDataService;
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
        log.info("조회 성공: {}", disasterData);
        // 필요한 데이터 가공 또는 처리 로직 추가
        return disasterData;
    }
    @PostMapping(value = "/local/info", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getLocalDisasterInfo(@RequestBody Map<String, String> request) {
        String location = request.get("location");
        // Redis에서 저장된 데이터를 조회하고 필요한 처리를 수행하여 반환하는 로직 작성
        String localDisasterData = localDisasterDataService.getLocalDisasterDataFromRedis(location);
        log.info("저장된 내용 조회 성공: {}", localDisasterData);
        // 필요한 데이터 가공 또는 처리 로직 추가
        return localDisasterData;
    }
}
