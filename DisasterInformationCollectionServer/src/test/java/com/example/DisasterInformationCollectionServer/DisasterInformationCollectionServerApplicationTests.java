package com.example.DisasterInformationCollectionServer;

import com.example.DisasterInformationCollectionServer.service.DisasterDataService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
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
