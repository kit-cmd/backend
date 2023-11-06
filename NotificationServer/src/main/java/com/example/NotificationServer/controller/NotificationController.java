package com.example.NotificationServer.controller;

import com.example.NotificationServer.dto.FCMNotificationRequestDto;
import com.example.NotificationServer.service.NotificationService;
import com.example.NotificationServer.service.SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@Slf4j
public class NotificationController {

    private final SubscribeService subscribeService;
    public final NotificationService notificationService;

    public NotificationController(SubscribeService subscribeService, NotificationService notificationService) {
        this.subscribeService = subscribeService;
        this.notificationService = notificationService;
    }

    @GetMapping("/start")
    public String startNotificationService() {
        subscribeService.start();
        return "Notification service started!";
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody String token){
        notificationService.register(token);
        log.info("등록:{}", token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/del")
    public ResponseEntity delete(@RequestBody String token){
        notificationService.delete(token);
        log.info("삭제:{}", token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/post")
    public String sendNotification (@RequestBody FCMNotificationRequestDto requestDto){
        log.info(String.valueOf(requestDto));
        return notificationService.sendMessage(requestDto);
    }
}
