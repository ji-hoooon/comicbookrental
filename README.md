# [만화책 대여 관리 시스템 프로젝트](https://github.com/ji-hoooon/comicbookrental)


## 목차
* [프로젝트 간단 요약](#프로젝트-간단-요약)<br>
* [프로젝트 정보/개요](#프로젝트-정보개요)<br>
* [프로젝트 설명](#프로젝트-설명)<br>
* [구현한 기능 설명](#구현한-기능-설명)<br>
* [개선해야할 점](#개선해야할-점)<br>
* [사용한 기술 스택](#사용한-기술-스택)<br>
* [테이블 DDL](#테이블-DDL)<br>

## 프로젝트 간단 요약
상용 가능한 만화책 대여 관리 시스템을 만들기 위한 프로젝트
* 스프링으로 컨트롤러와 서비스 처리
* MyBatis로 영속 계층 처리
* JSP로 화면 처리
* REST API와 Ajax으로 비동기 방식으로 반납 처리


## 프로젝트 정보/개요
|진행기간|목표|팀원|
|------|---|---|
|2023-01-26~2023-01-27 | 프로젝트 설계 |이지훈|
|2023-01-28 | 사용자, 만화책 CRUD 구현 |이지훈|
|2023-01-29 | 만화책 대여 및 반납 CRUD 구현 |이지훈|



## 프로젝트 설명

* ERD 
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FWyEYE%2FbtrXyoWlGFc%2FhG1vZLFkWs1NASnwZwAmqK%2Fimg.png">
          
* 클래스 다이어그램
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdStnTe%2FbtrXn64guIy%2FYJWzMWd3k28yOLlH2k6HoK%2Fimg.png">

## 구현한 기능 설명
* 로그인한 사용자만 만화책 조회 및 대여 가능
* 관리자만 만화책 CRUD 가능하며, 고객 관리하는 관리자 페이지가 존재한다
* 홈페이지에서 로그인하면, 대여 중인 정보를 확인할 수 있고 반납이 가능하다
* 만화책 정보 제목, 저자 기준 검색 및 페이징 구현

## 개선해야할 점
* 로그인하지 않은 사용자 직접 접근 제한 
* 회원가입 후, 자동 로그인 처리
* 디자인을 공유하는 페이지는 공통으로 처리하도록 변경
* 날짜와 시간에 대한 처리
* 만화책 수량을 지정해 0 이하인 경우 대여가 불가능하도록 구현하려고 했으나, 이상적으로 동작하지 않아 마무리하지 못함 (현재는 만화책 수량에 상관없이 대여가능)
* 대여중인 만화책에 대한 자세한 정보 변경
* 대여 테이블과 만화책 테이블 간에 외래키 관계 형성해, 대여중인 만화책을 삭제하지 못하도록 외래키 제약 조건 설정
* 회원가입시 아이디 중복체크
* 시간적 제약 때문에 부분적으로  -> 전체적으로 적용해 단계적으로 리팩토링 수행

## 테이블 DDL
```
create table comicbook
(
    cno       int auto_increment
        primary key,
    title     varchar(30)                          not null,
    writer    varchar(15)                          not null,
    publisher varchar(15)                          not null,
    `release` datetime   default CURRENT_TIMESTAMP null,
    quantity  int        default 1                 null,
    adult     tinyint(1) default 0                 null,
    up_date   datetime   default CURRENT_TIMESTAMP null,
    view_cnt  int        default 0                 null,
    reg_date  datetime   default CURRENT_TIMESTAMP null
);

create table rental
(
    rno         int auto_increment
        primary key,
    due_date    date                               null,
    cno         int                                not null,
    id          varchar(15)                        not null,
    rental_date datetime default CURRENT_TIMESTAMP not null
);

create table rentalhistory
(
    rno         int         not null
        primary key,
    cno         int         not null,
    id          varchar(15) not null,
    rental_date datetime    not null,
    return_date datetime    null
);

create table user
(
    uno            int auto_increment
        primary key,
    id             varchar(15)                          not null,
    birth          date                                 null,
    email          varchar(30)                          not null,
    tel            varchar(30)                          not null,
    sex            tinyint(1)                           null,
    sns            tinyint(1) default 0                 null,
    isdeniedrental date                                 null,
    isadmin        tinyint(1) default 0                 null,
    reg_date       datetime   default CURRENT_TIMESTAMP null,
    pwd            varchar(15)                          not null,
    name           varchar(10)                          not null,
    up_date        datetime   default CURRENT_TIMESTAMP null
);
```

## 사용한 기술 스택
![spring](https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JQuery](https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white)
![json](https://img.shields.io/badge/json-5E5C5C?style=for-the-badge&logo=json&logoColor=white)

