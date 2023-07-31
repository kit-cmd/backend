# 유저(인증) 서버
## 역할
|서비스|역할|
|---|---|
|유저 서버|사용자의 회원가입, 로그인을 담당하는 서비스|

## 기술스택
- Java 11
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- JWT
- MySQL

## 아키텍처
![아키텍처](https://github.com/kit-cmd/backend/assets/102667851/77b4910a-8076-4433-a921-c08eefd6a043)

## ERD
![유저서버erd](https://github.com/kit-cmd/backend/assets/102667851/462a335d-7471-4569-9ccc-55445a9a36dd)


## 제공 기능
|기능|설명|
|---|---|
|회원가입|- 회원가입을 통해 유저 정보를 저장|
|로그인|- 로그인시 JWT 토큰 발급|


## 프로젝트 진행 중 이슈

### JWT
- JSON 객체를 사용해서 토큰 자체의 정보를 저장하고 잇는 웹 토큰
- 헤비하지 않고, 간편하고, 쉽게 적용할 수 있다
![Untitled](https://github.com/kit-cmd/backend/assets/102667851/ab1d59f1-42ea-48cb-a46f-6829abe2bc10)



---
## 관련 자료

- [노션 자료](https://kyuhyun.notion.site/76b1e578df0542f2a4bf1141fd1152c5?pvs=4)
- [개인 공부 자료](https://github.com/freemoon99/study/tree/main/practie_springSecurity)
