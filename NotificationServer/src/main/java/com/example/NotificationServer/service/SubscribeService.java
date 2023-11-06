package com.example.NotificationServer.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubscribeService {

    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final NotificationService notificationService;

    public SubscribeService(RedisMessageListenerContainer redisMessageListenerContainer, NotificationService notificationService) {
        this.redisMessageListenerContainer = redisMessageListenerContainer;
        this.notificationService = notificationService;
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
        JSONObject jsonDataObject = new JSONObject(data);
        JSONArray disasterMsgArray = jsonDataObject.getJSONArray("DisasterMsg");
        JSONArray rowArray = disasterMsgArray.getJSONObject(1).getJSONArray("row"); // 변경된 부분
        JSONObject rowObject = rowArray.getJSONObject(0);
        String msg = rowObject.getString("msg");
        log.info("msg: {}", msg);

        try {
            Notification notification = Notification.builder()
                    .setTitle("실시간 재난 알림")
                    .setBody(msg) // Use the "msg" as the notification body
                    .build();

            Message notificationMessage = Message.builder()
                    .setNotification(notification)
                    .setTopic("change")
                    .build();
            try {
                String response = FirebaseMessaging.getInstance().send(notificationMessage);
                log.info("알림을 정상적으로 전송했습니다.: {}", response);
            } catch (FirebaseMessagingException e) {
                log.error("알림 전송 실패: {}", e.getMessage());
            }
        } catch (JSONException e) {
            log.error("Error parsing JSON data: {}", e.getMessage());
        }
    }
}
