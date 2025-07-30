# 테스트 환경
## java / springBoot
1. java : 21
    1. virtual thread 지원 Java 버전 중 가장 최신 LTS 사용 
2. Spring Boot : 3.3.5 
   1. Spring Data JPA
   2. Spring Web Starter 사용
   3. Security는 별도로 구현하지 않음
3. DB : postgresql
    1. docker를 통한 테스트 환경 구현 `docker-compose.yml` 참조

# DB 설정
## 테이블 생성
```postgresql
create schema test;

create table test.action_history
(
id            bigserial primary key, -- 기본 키 추가
user_id       bigserial,
action_type   varchar(50) not null,
action_target varchar(100),
action_count  integer default 1,
action_value  double precision,
action_time   timestamp   not null
);

create table test.user_last_action
(
    user_id          bigint not null primary key,
    last_action_time timestamp                                                        not null
);
```

## 인덱스 생성
### 유저아이디, 시간대별 통계 쿼리를 위한 커버링 인데스
```postgresql
create index idx_user_id_and_last_action_time
   on test.user_last_action (user_id, last_action_time);
```

### 요일별, 시간대별 통계 쿼리를 위한 커버링 인덱스
```postgresql
CREATE INDEX idx_ah_time_covering ON test.action_history (action_time, user_id)
   INCLUDE (action_type, action_target, action_count, action_value);
```