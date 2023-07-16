package com.example.NotificationServer.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubscribeService {

    private final RedisMessageListenerContainer redisMessageListenerContainer;

    public SubscribeService(RedisMessageListenerContainer redisMessageListenerContainer) {
        this.redisMessageListenerContainer = redisMessageListenerContainer;
    }

    public void start() {
        redisMessageListenerContainer.addMessageListener((MessageListener) (message, pattern) -> {
            String channel = new String(message.getChannel());
            String data = new String(message.getBody());
            log.info("변경된 새로운 채녈 {} 데이터: {}", channel, data);
            sendNotification(data);
        }, new ChannelTopic("change"));
        redisMessageListenerContainer.start();
    }

    private void sendNotification(String data) {
        // Customize the notification payload as per your requirements
        Notification notification = Notification.builder()
                .setTitle("Title")
                .setBody("Body")
                .build();

        Message notificationMessage = Message.builder()
                .setNotification(notification)
                .putData("data", data)
                .setTopic("change")
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(notificationMessage);
            log.info("알림을 정상적으로 전송했습니다.: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error("알림 전송 실패: {}", e.getMessage());
        }
    }
}
