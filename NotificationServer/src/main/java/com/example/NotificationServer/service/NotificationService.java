package com.example.NotificationServer.service;

import com.example.NotificationServer.dto.FCMNotificationRequestDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Message.Builder;
import com.google.firebase.messaging.Notification;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class NotificationService {
    private final List<String> tokenMap = new ArrayList<>();
    public void register(final String encodedToken){
        String token = URLDecoder.decode(encodedToken, StandardCharsets.UTF_8);
        if (!tokenMap.contains(token)) {
            tokenMap.add(token);
            log.info("리스트 저장: {}",tokenMap);

            // 서비스 사용자들을 기본 change 라는 토픽을 구독 시키기
            try {
                FirebaseMessaging.getInstance().subscribeToTopic(Collections.singletonList(token), "change");
                log.info("topic 구독 성공.");
            } catch (FirebaseMessagingException e) {
                log.error("topic 구독 실패: ", e);
            }
        } else {
            log.info("토큰 {}은 이미 리스트에 있습니다.", token);
        }
    }

    public void delete(final String token){
        tokenMap.remove(token);
        log.info("리스트 삭제:{}", tokenMap);

        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(Collections.singletonList(token), "change");
            log.info("topic 구독 삭제 성공.");
        } catch (FirebaseMessagingException e) {
            log.error("topic 구독 삭제 실패: ", e);
        }
    }

    public List<String> getToken(){
        return tokenMap;
    }

    public String sendMessage(FCMNotificationRequestDto requestDto){
        for (String token : tokenMap) {
            // Create a message.
            Message message = Message.builder()
                    .setToken(token)
                    .putData("title", requestDto.getTitle())
                    .putData("body", requestDto.getBody())
                    .build();

            try {
                // Send the message.
                String response = FirebaseMessaging.getInstance().send(message);

                log.info("Message sent to {}: {}", token, response);

            } catch (FirebaseMessagingException e) {
                log.error("Failed to send message to {}: ", token, e);

                return "알림 전송에 실패하였습니다.";
            }
        }

        return "알림 전송 성공";
    }

}
