package com.example.DisasterInformationCollectionServer;

import com.example.DisasterInformationCollectionServer.service.DisasterDataService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DisasterInformationCollectionServerApplicationTests {

	private final DisasterDataService disasterDataService; // 생성자 주입

	public DisasterInformationCollectionServerApplicationTests(DisasterDataService disasterDataService) {
		this.disasterDataService = disasterDataService;
	}

	@Test
	public void testGetValueFromRedis() {
		String key = "myKey";
		String expectedValue = "myValue";

		String actualValue = disasterDataService.getValueFromRedis(key); // 수정 필요

		assertEquals(expectedValue, actualValue);
	}
}
