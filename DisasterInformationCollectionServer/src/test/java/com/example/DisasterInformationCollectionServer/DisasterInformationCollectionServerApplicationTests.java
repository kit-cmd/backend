package com.example.DisasterInformationCollectionServer;

import com.example.DisasterInformationCollectionServer.service.DisasterDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DisasterInformationCollectionServerApplicationTests {

	private final DisasterDataService disasterDataService; // 생성자 주입

	@Autowired
	public DisasterInformationCollectionServerApplicationTests(DisasterDataService disasterDataService) {
		this.disasterDataService = disasterDataService;
	}

	@Test
	public void testGetValueFromRedis() {
		String key = "myKey";
		String expectedValue = "myValue";

		String actualValue = disasterDataService.getValueFromRedis(key);

		assertEquals(expectedValue, actualValue);
	}
}
