# 알림 서버
## 역할
|서비스|역할|
|---|---|
|알림 서버|웹푸시 알림을 보냄|

![image](https://github.com/kit-cmd/backend/assets/102667851/6a9b4882-a089-454e-8bfb-2dd47dc0f69b)


## 기술스택
- Java 11
- Spring Boot
- Spring MVC
- Redis
- firebase FCM

## 아키텍처
![아키텍처](https://github.com/kit-cmd/backend/assets/102667851/721fed4c-b28a-494a-a70c-0ad2aedc86a7)


## 제공 기능
|기능|설명|
|---|---|
|알림 기능|- firebase의 FCM을 이용하여 알림 전송|

## 프로젝트 진행 중 이슈

### 알림 전송
```
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

            List<String> tokens = notificationService.getToken();
            for (String token : tokens) {
                Message notificationMessage = Message.builder()
                        .setNotification(notification)
                        .setToken(token)
                        .build();

                try {
                    String response = FirebaseMessaging.getInstance().send(notificationMessage);
                    log.info("알림을 정상적으로 전송했습니다.: {}", response);
                } catch (FirebaseMessagingException e) {
                    log.error("알림 전송 실패: {}", e.getMessage());
                }
            }
        } catch (JSONException e) {
            log.error("Error parsing JSON data: {}", e.getMessage());
        }
    }
```




---
## 관련 자료
- [노션 자료](https://kyuhyun.notion.site/bd8a8ef6f02e4a6ba48dcb8f2588b2a5?pvs=4)
- [개인 공부 자료](https://github.com/freemoon99/study/tree/main/practie_springSecurity)
- [https://headf1rst.github.io/TIL/push-notification#6.-fcm을-spring-boot-프로젝트에-적용하기](https://headf1rst.github.io/TIL/push-notification#6.-fcm%EC%9D%84-spring-boot-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EC%97%90-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0)
- [https://velog.io/@skygl/FCM-Spring-Boot를-사용하여-웹-푸시-기능-구현하기#토큰-관리](https://velog.io/@skygl/FCM-Spring-Boot%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-%EC%9B%B9-%ED%91%B8%EC%8B%9C-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0#%ED%86%A0%ED%81%B0-%EA%B4%80%EB%A6%AC)
- [https://velog.io/@alsduq1117/스프링부트-FCM-푸쉬-알림-서버#serverspring-boot](https://velog.io/@alsduq1117/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-FCM-%ED%91%B8%EC%89%AC-%EC%95%8C%EB%A6%BC-%EC%84%9C%EB%B2%84#serverspring-boot)
