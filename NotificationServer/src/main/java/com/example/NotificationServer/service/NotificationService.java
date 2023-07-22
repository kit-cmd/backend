package com.example.NotificationServer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NotificationService {
    private final List<String> tokenMap = new ArrayList<>();
    public void register(final String token){
        tokenMap.add(token);
        log.info("리스트 저장: {}",tokenMap);
    }

    public void delete(final String token){
        tokenMap.remove(token);
        log.info("리스트 삭제:{}", tokenMap);
    }

    public List<String> getToken(){
        return tokenMap;
    }
}
