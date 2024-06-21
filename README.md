# &nd - 개발자를 위한 공모전 및 커뮤니티 프로젝트 (Back API Server)
***
## 📕 Tech Stacks ##
<div align= "left">
<h3> Backend</h3>
<img src="https://img.shields.io/badge/intelliJ-F80000?style=flat&logo=IntelliJ IDEA&logoColor=black">
<img src="https://img.shields.io/badge/github-181717?style=flat&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/Java 17-007396?style=flat&logo=Java&logoColor=white">
<img src="https://img.shields.io/badge/jsonwebtokens-000000?style=flat&logo=jsonwebtokens&logoColor=white">
<img src="https://img.shields.io/badge/gradle 8-02303A?style=flat&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot 3.1.2-6db33f?style=flat&logo=springBoot&logoColor=white">
<img src="https://img.shields.io/badge/Spring Security 6.1.2-6db33f?style=flat&logo=SpringSecurity&logoColor=white">
<img src="https://img.shields.io/badge/Stomp-000000?style=flat&logo=stomp&logoColor=white">
<img src="https://img.shields.io/badge/SSE-E7282D?style=flat&logo=SSE&logoColor=white">
<img src="https://img.shields.io/badge/Spring Data Jpa-EB5424?style=flat&logo=oauth&logoColor=white">
<img src="https://img.shields.io/badge/HTML 5-E34F26?style=flat&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/CSS 3-1572B6?style=flat&logo=CSS3&logoColor=white">
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white">
<img src="https://img.shields.io/badge/OAuth-3B66BC?style=flat&logo=auth0&logoColor=white">
<img src="https://img.shields.io/badge/MySql 8-4479a1?style=flat&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/Redis-FF4438?style=flat&logo=mRedis&logoColor=white">
</div>

## 목적
개발자를 위한 개발 카테고리별 공모전을 볼 수 있고, 개발자들 간에 정보 공유 및 소통을 위한 커뮤니티 사이트를 만들어보는 것을 목표로 하였다.

## 팀 노션
https://www.notion.so/4riPlus-cb08768d12ae4be8a575332d60f7d1cf

## ERD
https://miro.com/app/board/uXjVMtvWk0c=/
![image](https://private-user-images.githubusercontent.com/51190093/341710063-b8494c43-7863-459f-a0c1-fc50415da234.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTg5NjEzMTQsIm5iZiI6MTcxODk2MTAxNCwicGF0aCI6Ii81MTE5MDA5My8zNDE3MTAwNjMtYjg0OTRjNDMtNzg2My00NTlmLWEwYzEtZmM1MDQxNWRhMjM0LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjIxVDA5MTAxNFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWQwZjYyNDIzMjdlZjZiZDI0ODA4MDZjYjNlZTAyYTUzZTk4YTUyOTA1Mzg3MzRjYzVmMDVhOGMwZjhkYTEwYTMmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.dSOJzbEslNnNLWsyPrDTt5cIZcJcH-WhdOGrO9My9rM)
## API 명세
https://www.notion.so/4riPlus-S-A-cb08768d12ae4be8a575332d60f7d1cf?pvs=4#fa6719be2a6c426e9927bbce91ec523a

## 프로젝트 기능 및 설계 ##
- 필수 기능
    - 사용자 인증: OAuth
    - 게시글 CRUD, 조회수
    - 팀모집 게시글 CRUD, 조회수
    - 자유게시판 게시글에 대한 댓글 CRUD
        - 비밀댓글, 대댓글
        - 댓글 신고 기능
    - 카테고리 구현
    - 북마크, 공유, 링크복사
    - 알림 기능 ( 프로필 - 카테고리 북마크 시, 해당 공모전 등록 시 알림 )

- 핵심 기능
    - [AWS S3] 이미지 업로드 / 파일 다운로드(참가 신청서, 공모전 지침서)
    - [WebSocket] 채팅기능 : 1:1채팅 
      → 1:1 채팅만 가능
      → 채팅 기록 남아야될 것
      → 채팅 목록 실시간으로 (알람기능)
    - [Scheduling] 마감기한 설정 ( 게시글의 마감기한 이후, 게시글 상태(모집중, 모집마감) 변경 )
    

- 부가 기능
    - 백오피스 페이지 분리 (프로젝트, 브랜치 따로 제작 - db는 같이 쓰고)
    - 백오피스 로그인 방식 - 슈퍼관리자를 초기데이터 생성 시 db에 밀어넣기
    - 백오피스 노출해서 관리할 기능
      → 하위 관리자 CRUD
        

- 백오피스
    - 전체 게시글 목록화 
    - 신고된 게시글 전체 조회 및 삭제
    - 신고한 테이블 id
    - 참조하고 있는 게시글 id(FK)
    - 신고 누적횟수
    - 신고 누적횟수에 따른 차순
    - 신고된 댓글 전체 조회 및 삭제
    - 신고한 테이블 id
    - 참조하고 있는 댓글 id(FK)
    - 신고 누적횟수
    - 신고 누적횟수에 따른 차순 (내림차순 정렬)
    - 관리자 CRUD
    - 공모전 CRUD
    - 카테고리 CRUD - 대분류,중분류,소분류
    - 유저 관리 CRUD
    - 블랙리스트 제재 방향성
    - 관리자 로그인/로그아웃
    - 채팅기능
    - 이미지 필수 구현

## 기술적 의사 결정 및 트러블 슈팅
- 전 팀원이 회의에 참여를 하면서 프로젝트 진행 사항과 방향에 대한 고민을 하고, 진심으로 참여하여 정확한 이해와 적절하게 사용을 위해 노력함.
- 코드 변수명을 카멜 케이스로 통일했으며, 각 기능별 명칭에 대해 통일성을 유지할 수 있도록 몇 차례 회의를 거쳤고, 그 회의 결과를 준수함.
- 특히 깃 관리에 있어 브랜치 분리를 잘했고, 정기적으로 진행된, 꾸준한 리뷰와 PR을 통해 충돌을 예방하려고 노력함.
- 카테고리 구현 : 하위 카테고리를 구현하기로 결정했을 당시 셀프참조 카테고리로 구성할 것인지, 하위 카테고리를 만들어서 해결할 것인지에 대한 고민이 있었고, 셀프참조 카테고리를 구현할 경우, 무한정 늘어날 수 있고, 이는 셀렉트 쿼리가 많이 발생할 수 있기 때문에 비효율적이라고 판단하여 하위 카테고리로 구성하도록 결정함.
- 로그아웃을 레디스를 사용한 이유 : 리프레시 토큰을 통해 로그아웃 구현 시 장점이 있고, 읽고 쓰기가 MySQL 보다 훨씬 빠른 속도를 자랑하기 때문에 사용하도록 결정함.
- 백 오피스의 생성 : 서버와 백오피스 분리를 할 것인지, 분리하지 않을 것인지에 대한 고민이 있었고, 보안 이슈를 고려해 분리하도록 결정함. 결정 시에도 관리자를 어떻게 지정할 것인지, 그리고 관리자의 권한을 어떻게 부여할 것인지에 대한 고민이 있었으며 이를 멘토링과 의사결정으로 해결.
- OAuth 로그인만 구현한 이유 : 일반 로그인의 경우, DB 관리가 쉽지 않고, 비용적인 측면에서 과도한 비용이 발생할 수 있기때문에 지양함.
- 마감기한의 설정 : 스케줄링을 사용하지 않고, if문을 통해 해결했으며 이는, 스케줄링을 사용할 경우 앞으로 데이터가 쌓여 나간다면 차차 시간이 밀려 언젠간 시간이 밀리는 현상이 발생할 수 있고, 마감 기한처럼 시간을 준수해야 하는 기능에 알맞지 않을 것이라고 여겨졌기 때문임.
- 채팅 내역 보관기간 설정 : 채팅이 주된 목적이 아니고 공모전을 위한 간단한 채팅정도가 목적이기 때문에 채팅 내역을 영구적으로 보관하는 방법(Redis + Mysql 두 곳에 채팅 내역을 저장) 보다는 Redis에 일정기간에 보관하여 저장하는 방법으로 설계하기로 결정하였다. ( 보관 기간 : 60일 )

## 시연 영상
- oAuth 로그인, 공모전 카테고리 및 상세, 자유게시판 등록 및 조회, 댓글 등록/신고, 게시글 신고, 신고된 게시글/댓글 조회

https://private-user-images.githubusercontent.com/51190093/341716987-58897315-4424-4eda-a065-d16d6244643b.mp4?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTg5NjI3NzIsIm5iZiI6MTcxODk2MjQ3MiwicGF0aCI6Ii81MTE5MDA5My8zNDE3MTY5ODctNTg4OTczMTUtNDQyNC00ZWRhLWEwNjUtZDE2ZDYyNDQ2NDNiLm1wND9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjIxVDA5MzQzMlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWMxZDEzOTQ1YWNkMDc0ZTUwYzVkNTU4YjE4MWIzZTBiYTljMjQwYzJhZWQzNjlhNmNhNmI4Yjk5MjQ5YjVjM2ImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.PNBujhC1qYAKUQQpK871H1ddWaPLiRzJunxSBFudDaE

- 백오피스 카테고리 생성, 공모전 등록, 관리자 생성 및 삭제

https://private-user-images.githubusercontent.com/51190093/341714280-d89a5799-ac35-4222-8d61-62b07c91947a.mp4?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTg5NjIxOTUsIm5iZiI6MTcxODk2MTg5NSwicGF0aCI6Ii81MTE5MDA5My8zNDE3MTQyODAtZDg5YTU3OTktYWMzNS00MjIyLThkNjEtNjJiMDdjOTE5NDdhLm1wND9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjIxVDA5MjQ1NVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTdiMGM2ZGY4NWE3YmRhMzI4NDczNWM1ZGZjMzI0NGM0NTAxN2Q2OWIwZjhlYmQ3NzEzNjIwNjYzODViMmI1N2ImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.C37I7qXxYhBI60cpe-GSHxwRItjasE0O2s3xDUism1U

- 채팅 및 알림 기능

https://private-user-images.githubusercontent.com/51190093/341714268-2eb19921-21a8-497f-8137-d035fd2a282c.mp4?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTg5NjIxOTUsIm5iZiI6MTcxODk2MTg5NSwicGF0aCI6Ii81MTE5MDA5My8zNDE3MTQyNjgtMmViMTk5MjEtMjFhOC00OTdmLTgxMzctZDAzNWZkMmEyODJjLm1wND9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjIxVDA5MjQ1NVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTI1YjlmOTQzYWQzZTBjN2MyMmQxNmM0ZDI2ZWY3MjYwZWJkMDBlOTlkMGE5NWZiYzhmMTRmNjlmMWZiMjU3YjQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.1Pxx4pag6cWMl_dxQUY5Sy2OFjl_uvYKREeY4mTgefA


