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
4. 실제 데이터를 활용하는 로직을 테스트시
   1. `test.java.study.temporal_behavior_analytics.data.DataInitializer.java` 클래스 이용
   2. 환경에 따라 Thread 수를 늘릴 수도, 줄일 수도 있음
5. Wau, Mau 파악 및 통계를 상정하고 개발이 진행되고 있으므로 controller는 `/wau`, `/mau`로 restApi의 특징을 부여

# DB 설정
## 테이블 생성
```postgresql
create schema test;

CREATE TABLE test.action_history
(
   id            bigint, -- JPA 를 위한 키 추가
   user_id       bigint not null,
   action_type   varchar(50) not null,
   action_target varchar(100),
   action_count  integer default 1,
   action_value  double precision,
   action_time   timestamp not null
) PARTITION BY RANGE (action_time);

create table test.user_last_action
(
    user_id          bigint not null primary key,
    last_action_time timestamp                                                        not null
);
```
> 파티션 테이블을 상정하고 데이터를 생성하기 때문에 id는 JPA의 ID 생성 전략을 Snowflake의 ID 생성 전략으로 처리


## 파티션 테이블 생성
```postgresql
-- 2024년 파티션
CREATE TABLE action_history_2024_07 PARTITION OF test.action_history
   FOR VALUES FROM ('2024-07-01') TO ('2024-08-01');

CREATE TABLE action_history_2024_08 PARTITION OF test.action_history
   FOR VALUES FROM ('2024-08-01') TO ('2024-09-01');

CREATE TABLE action_history_2024_09 PARTITION OF test.action_history
   FOR VALUES FROM ('2024-09-01') TO ('2024-10-01');

CREATE TABLE action_history_2024_10 PARTITION OF test.action_history
   FOR VALUES FROM ('2024-10-01') TO ('2024-11-01');

CREATE TABLE action_history_2024_11 PARTITION OF test.action_history
   FOR VALUES FROM ('2024-11-01') TO ('2024-12-01');

CREATE TABLE action_history_2024_12 PARTITION OF test.action_history
   FOR VALUES FROM ('2024-12-01') TO ('2025-01-01');

-- 2025년 파티션
CREATE TABLE action_history_2025_01 PARTITION OF test.action_history
   FOR VALUES FROM ('2025-01-01') TO ('2025-02-01');

CREATE TABLE action_history_2025_02 PARTITION OF test.action_history
   FOR VALUES FROM ('2025-02-01') TO ('2025-03-01');

CREATE TABLE action_history_2025_03 PARTITION OF test.action_history
   FOR VALUES FROM ('2025-03-01') TO ('2025-04-01');

CREATE TABLE action_history_2025_04 PARTITION OF test.action_history
   FOR VALUES FROM ('2025-04-01') TO ('2025-05-01');

CREATE TABLE action_history_2025_05 PARTITION OF test.action_history
   FOR VALUES FROM ('2025-05-01') TO ('2025-06-01');

CREATE TABLE action_history_2025_06 PARTITION OF test.action_history
   FOR VALUES FROM ('2025-06-01') TO ('2025-07-01');

CREATE TABLE action_history_2025_07 PARTITION OF test.action_history
   FOR VALUES FROM ('2025-07-01') TO ('2025-08-01');

CREATE TABLE action_history_2025_08 PARTITION OF test.action_history
   FOR VALUES FROM ('2025-08-01') TO ('2025-09-01');
```
> 생성이 완료된 시점부터 각 월별로 스케줄을 통한 파티셔닝 테이블 생성

## 인덱스 생성
### 유저아이디, 시간대별 통계 쿼리를 위한 인데스
```postgresql
create index idx_user_id_and_last_action_time
   on test.user_last_action (user_id, last_action_time);
```

### 요일별, 시간대별 통계 쿼리를 위한 인덱스
```postgresql
CREATE INDEX idx_action_history_time_type_user_count ON test.action_history (action_time, action_type, user_id);
CREATE INDEX idx_action_history_target_time_user ON test.action_history (action_target, action_time, user_id);
CREATE INDEX idx_action_history_user_id ON test.action_history (user_id);
```