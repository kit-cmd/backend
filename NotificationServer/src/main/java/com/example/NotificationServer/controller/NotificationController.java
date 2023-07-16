package com.example.NotificationServer.controller;

import com.example.NotificationServer.service.SubscribeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final SubscribeService subscribeService;

    public NotificationController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    @GetMapping("/start")
    public String startNotificationService() {
        subscribeService.start();
        return "Notification service started!";
    }
}
