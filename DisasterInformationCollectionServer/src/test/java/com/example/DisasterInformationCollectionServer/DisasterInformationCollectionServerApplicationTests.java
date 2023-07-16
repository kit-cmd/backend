package com.example.DisasterInformationCollectionServer;

import com.example.DisasterInformationCollectionServer.service.DisasterDataService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;


@SpringBootTest
class DisasterInformationCollectionServerApplicationTests {
	@MockBean
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private DisasterDataService disasterDataService;
	@Disabled
	@Test
	void contextLoads() {
	}

}
