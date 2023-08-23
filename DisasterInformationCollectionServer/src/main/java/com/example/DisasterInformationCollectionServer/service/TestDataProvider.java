package com.example.DisasterInformationCollectionServer.service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestDataProvider {
    public static Map<String, String> getTestData() {
        Map<String, String> testData = new HashMap<>();
        testData.put("myKey", "myValue");
        // 추가로 테스트 데이터를 설정할 수 있습니다.
        return testData;
    }
}
