package com.example.NotificationServer;

import com.example.NotificationServer.config.RedisConfig;
import com.example.NotificationServer.service.NotificationService;
import com.example.NotificationServer.service.SubscribeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@SpringBootApplication
@Import(RedisConfig.class)
public class NotificationServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NotificationServerApplication.class, args);
		RedisMessageListenerContainer redisMessageListenerContainer = context.getBean(RedisMessageListenerContainer.class);
		NotificationService notificationService = context.getBean(NotificationService.class);
		SubscribeService subscribeService = new SubscribeService(redisMessageListenerContainer, notificationService);
		subscribeService.start();
	}

}
