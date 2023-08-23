package com.example.DisasterInformationCollectionServer;

import com.example.DisasterInformationCollectionServer.service.DisasterDataService;
import com.example.DisasterInformationCollectionServer.service.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DisasterInformationCollectionServerApplicationTests {

	@Test
	public void testGetValueFromRedis() {
		// Mocking the DisasterDataService using Mockito
		DisasterDataService disasterDataService = mock(DisasterDataService.class);

		// Creating test data using TestDataProvider
		Map<String, String> testData = TestDataProvider.getTestData();

		// Setting up the mock behavior to return test data
		when(disasterDataService.getValueFromRedis("myKey")).thenReturn(testData.get("myKey"));

		// Using the mock to test the behavior
		String expectedValue = "myValue";
		String actualValue = disasterDataService.getValueFromRedis("myKey");

		assertEquals(expectedValue, actualValue);
	}
}
