# 하천 수위 예측 프로젝트
## ERD
<image src='ERD.PNG'>

## REST API
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
