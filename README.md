# Team CMD
![Logo](https://github.com/kit-cmd/.github/assets/102667851/634c8dd0-9af6-431c-9cdc-8dc9a837fdf3)

## 프로젝트 소개

### 재난대응 가이드 챗봇 개발
"재난대응 가이드 챗봇 개발"은 재난 상황 발생시 대피&대응 지원을 위한 공공서비스 지원 필요성을 해소시킬 수 있도록 한다.

![image](https://github.com/kit-cmd/backend/assets/102667851/34b1d11d-e7c6-4c91-bf6b-aa3ad6d14698)


프로젝트 이미지, 배포 주소 등 추가 예정

## 팀원 소개
| 전용현 | 박규현 |
|:------:|:------:|
|![young] | ![kyu]|
| 백엔드 | 백엔드 |
| [Raccooon98](https://github.com/Raccooon98) | [freemoon99](https://github.com/freemoon99) |

## 기술 스택
### Backend
- Java 11
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- Spring Integration

### Infrastructure
- MySQL
- Redis
- AWS
- github Action
- docker

## 역할 분담
### 박규현
- [유저 서버](https://github.com/kit-cmd/backend/tree/main/UserServer)
- [재난정보 수집 서버](https://github.com/kit-cmd/backend/tree/main/DisasterInformationCollectionServer)
- [알림 서버](https://github.com/kit-cmd/backend/tree/main/NotificationServer)

### 전용현
- 재난 대피소 정보 서버

## 프로젝트 관련 문서

[📌PMP 문서](https://github.com/kit-cmd/.github/files/11522099/CMD.pptx) <br/>
[📒노션 페이지](https://www.notion.so/ICT-d4c12cd695e646348ff91f7086f911c4)


## 프로젝트중 이슈 발생 (통합)
***개별 이슈는 각 폴더에 README.md 파일로 첨부하였습니다.***
### CI/CD 적용 문제 (githubaction + Docker + Docker-Compose + ec2)
- redis 적용 실패
  - redis를 githubaction으로 ci 적용을 위해서는 Embedded Redis를 사용해야함 (테스트를 위함)
- java 버전 설정
  -  ```name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'zulu'```
- MySQL 적용 실패
  - AWS RDS로 변경하여 해결
- Docker 경로 관련 이슈
- ec2 배포 실패(현재 해결중)

<!-- Icon Refernces -->
[kyu]: https://user-images.githubusercontent.com/102667851/230325642-ba742aed-6f63-469c-bfb6-b5aefcf2d967.png
[young]: https://user-images.githubusercontent.com/102667851/230325661-48c39145-28f7-40d5-ac5e-79838bcb3d8b.png
