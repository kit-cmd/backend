package com.example.NotificationServer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FCMNotificationRequestDto {
    private String targetUserId;
    private String title;
    private String body;

    @Builder
    public FCMNotificationRequestDto(String targetUserId, String title, String body){
        this.targetUserId = targetUserId;
        this.body = body;
        this.title = title;
    }
}
