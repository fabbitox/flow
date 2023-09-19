# 하천 수위 예측 프로젝트 [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.3-brightgreen)](https://spring.io/projects/spring-boot) [![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
<image src='System.png'>

## ERD
<image src='ERD.PNG'>

## REST API
관리자 로그인: admin abcd
### GET
    연락처 리스트(관리자)
    contact/list

    알람 내역(관리자)
    alarm

    경계 값
    criteria

    통제 지점
    ctrlpoint

    통제 구역
    ctrlarea

    대피소
    shelter
### POST
    로그인
    login

    대피소 추가(관리자)
    shelter

    연락처 추가
    contact
### DELETE(관리자)
    연락처 삭제
    contact/{id}

    대피소 삭제
    shelter/{id}

## WebSocket
ws://localhost:8080/pushservice

## 시작 가이드
MySQL에서 data 폴더 안의 sql 실행

src/main/resources/application.properties 파일에 작성
```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

flow.file-save-path=
flask.url=
fcst.service-key=
fcst.url=https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?serviceKey=%s&pageNo=1&numOfRows=1000&dataType=JSON&base_date=%s&base_time=%s&nx=%s&ny=%s
```
