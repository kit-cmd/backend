# 재난 문자 수집 서버
## 역할
|서비스|역할|
|---|---|
|재난 문자 수집 서버|재난 문자를 수집하여 알림서버로 pub|

## 기술스택
- Java 11
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- JWT
- Redis

## 아키텍처
![아키텍처](https://github.com/kit-cmd/backend/assets/102667851/721fed4c-b28a-494a-a70c-0ad2aedc86a7)


## 제공 기능
|기능|설명|
|---|---|
|재난 정보 수집 기능|- api를 요청하여 재난 정보를 수집|
|스케쥴링 기능|- 정해진 시간마다 api 요청|
|Redis 메세지 브로커 기능|- 알림서버가 구독한 topic으로 pub|

## 프로젝트 진행 중 이슈

### 스케쥴러 에러
@Enable스케쥴러 라는 어노테이션을 사용하지 않음 
```
... 생략

@Component
@Slf4j
@EnableScheduling
public class DataUpdateScheduler {
    private final DisasterDataService disasterDataService;
    private final LocalDisasterDataService localDisasterDataService;
    private final List<String> locationList;
    private final RedisTemplate<String, String> redisTemplate;

... 생략

```

### redis를 메세지 브러커 형식으로 사용하여 알림 → 메세지 pub/sub구조인데 이는 데이터를 저장하지 않음
재난 정보를 수집하여 새로 업데이트 된 내용을 pub 요청
```
public void fetchData() throws IOException {
        String urlBuilder = API_URL + "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + API_KEY +
                "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8);

        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        StringBuilder sb = new StringBuilder();

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.info("에러 발생: {}", e);
        } finally {
            conn.disconnect();
        }

        String responseData = sb.toString();
        redisTemplate.opsForValue().set(REDIS_KEY, responseData);
        log.info("redis에 저장됨:{}", responseData);
    }
```


### 처음 구현 했을 때 → 순환 구조라고 에러가 발생
```
package com.example.NotificationServer.service;

import com.example.NotificationServer.subscriber.DiasterSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class NotificationService {
    private final DiasterSubscriber diasterSubscriber;

    public NotificationService(DiasterSubscriber diasterSubscriber) {
        this.diasterSubscriber = diasterSubscriber;
    }

    @PostConstruct
    public void initialize() {
        startSubscription();
    }

    public void startSubscription() {
        // 구독 로직 구현
        diasterSubscriber.subscribeToChannel();
    }

    public void sendNotification(String eventData) {
        // 알림 전송 로직 구현
        // ...
        log.info("Notification sent: " + eventData);
    }
}

///////////

package com.example.NotificationServer.subscriber;

import com.example.NotificationServer.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
public class DiasterSubscriber {

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisMessageListenerContainer messageListenerContainer;
    private final NotificationService notificationService;

    @Autowired
    public DiasterSubscriber(RedisTemplate<String, String> redisTemplate, RedisMessageListenerContainer messageListenerContainer, NotificationService notificationService) {
        this.redisTemplate = redisTemplate;
        this.messageListenerContainer = messageListenerContainer;
        this.notificationService = notificationService;
    }

    public void subscribeToChannel() {
        messageListenerContainer.addMessageListener((message, pattern) -> {
            String eventData = message.toString();
            // 이벤트 데이터 처리 로직을 구현
            log.info("Received event: " + eventData);
            // 알림 서비스를 통해 알림 전송
            notificationService.sendNotification(eventData);
        }, Collections.singletonList(new ChannelTopic("disasterDataChannel")));
    }
}
```

## 해결하지 못한 문제

### 키워드로 커스텀 하는것이 쉽지가 않음
- 태그등과 같은 내용으로 분리가 되어있지 않음으로 해결 불가
- 지역별 api를 이용하여 해결하려고 함 -> api 1000회 이하로 제한되어있고, 정보가 바르게 입력되지 않음

---
## 관련 자료
- [Spring Boot 일정시간마다 실행시키기](https://andonekwon.tistory.com/70)
- [노션 자료](https://kyuhyun.notion.site/bd8a8ef6f02e4a6ba48dcb8f2588b2a5?pvs=4)
- [개인 공부 자료](https://github.com/freemoon99/study/tree/main/practie_springSecurity)
- [redis 메세지 브로커1](https://brunch.co.kr/@springboot/374)
- [redis 메세지 브로커2](https://www.daddyprogrammer.org/post/3688/redis-spring-data-redis-publish-subscribe/)
- [https://velog.io/@kth121211/메시지-브로커](https://velog.io/@kth121211/%EB%A9%94%EC%8B%9C%EC%A7%80-%EB%B8%8C%EB%A1%9C%EC%BB%A4)
