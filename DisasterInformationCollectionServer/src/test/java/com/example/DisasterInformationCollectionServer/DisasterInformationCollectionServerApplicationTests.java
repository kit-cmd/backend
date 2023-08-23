package com.example.DisasterInformationCollectionServer;

import com.example.DisasterInformationCollectionServer.service.TestDataProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisasterInformationCollectionServerApplicationTests {

	@Test
	public void testGetValueFromRedis() {
		String key = "myKey";
		String expectedValue = "myValue";

		String actualValue = DisasterDataService.getValueFromRedis(key); // 수정 필요

		assertEquals(expectedValue, actualValue);
	}
}
