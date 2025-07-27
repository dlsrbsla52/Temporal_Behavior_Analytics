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

create table user_last_action
(
    user_id          bigint default nextval('test.user_last_action_id_seq'::regclass) not null
        primary key,
    last_action_time timestamp                                                        not null
);
```

### 1. action_history 테이블 시퀀스 생성
```postgresql
CREATE SEQUENCE test.action_history_user_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
```

### 2. 시퀀스 소유자 설정
```postgresql
ALTER SEQUENCE test.action_history_user_id_seq OWNER TO postgres;
```

### 3. 테이블 컬럼에 시퀀스 기본값 적용
```postgresql
ALTER TABLE test.action_history
ALTER COLUMN id SET DEFAULT nextval('test.action_history_user_id_seq');
```

### 4. 시퀀스와 테이블 컬럼 연결
```postgresql
ALTER SEQUENCE test.action_history_user_id_seq OWNED BY test.action_history.id;
```


### 1. user_last_action 테이블 시퀀스 생성
```postgresql
CREATE SEQUENCE test.user_last_action_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
```

### 2. 시퀀스 소유자 설정
```postgresql
ALTER SEQUENCE test.user_last_action_id_seq OWNER TO postgres;
```

### 3. 테이블 컬럼에 시퀀스 기본값 적용
```postgresql
ALTER TABLE test.user_last_action
ALTER COLUMN user_id SET DEFAULT nextval('test.user_last_action_id_seq');
```

### 4. 시퀀스와 테이블 컬럼 연결 (소유권 설정)
```postgresql
ALTER SEQUENCE test.user_last_action_id_seq OWNED BY test.user_last_action.user_id;
```

## 인덱스 생성
### 요일별, 시간대별 통계 쿼리를 위한 커버링 인덱스
```postgresql
CREATE INDEX idx_action_history_time_type_user_count
ON test.action_history (action_time, action_type, user_id, action_count);
```

### 특정 타겟 액션 사용자 필터링을 위한 커버링 인덱스
```postgresql
CREATE INDEX idx_action_history_target_time_user
ON test.action_history (action_target, action_time, user_id);
```

### 최근 활동 사용자 필터링을 위한 인덱스
```postgresql
CREATE INDEX idx_user_last_action_time
ON test.user_last_action (last_action_time);
```