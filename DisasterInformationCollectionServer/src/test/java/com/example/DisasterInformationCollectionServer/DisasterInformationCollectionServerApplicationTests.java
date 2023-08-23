package com.example.DisasterInformationCollectionServer;

import com.example.DisasterInformationCollectionServer.service.DisasterDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class DisasterInformationCollectionServerApplicationTests {
	@MockBean
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private DisasterDataService disasterDataService;

	@Test
	void testRedisOperations() {
		// Mock Redis 연산 설정
		when(redisTemplate.opsForValue().get("myKey")).thenReturn("myValue");

		// 실제 Redis 연산 수행
		String value = disasterDataService.getValueFromRedis("myKey");

		// 결과 확인
		assertEquals("myValue", value);
	}
}
