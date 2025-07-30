package study.temporal_behavior_analytics.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import study.temporal_behavior_analytics.common.config.SnowflakeIdGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 대용량 테스트 데이터를 초기화하는 클래스입니다.
 * 주의: 실행 시간이 매우 오래 걸리므로, 필요시에만 @Disabled를 해제하고 실행하십시오.
 */
@Slf4j
@ActiveProfiles("local")
@SpringBootTest
//@Disabled
public class DataInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;


    // ================== 데이터 생성 규칙 상수 ==================
    private static final int USER_COUNT = 3_000_000;

    // 실행 환경에 따라 조절
    private static final int THREAD_COUNT = 20;

    // action_history 생성을 위한 외부 루프 횟수
    private static final int EXECUTE_COUNT = 200_000;
    
    // 한 번의 배치로 삽입할 레코드 수
    // 총 ActionHistory 레코드 수 = EXECUTE_COUNT * BULK_INSERT_SIZE = 4억
    // 문제에서 약 4억개의 튜플 수를 가지고 있다고 가정 했기 때문에 최대 입력 튜플을 4억으로 처리
    private static final int BULK_INSERT_SIZE = 2_000;

    // 임의의 actionType 정의
    private static final List<String> ACTION_TYPES = List.of("VIEW", "CLICK", "VOTE");

    // 임의의 actionTarget 정의
    private static final List<String> ACTION_TARGETS = List.of("vote", "product_1", "product_2", "product_3");



    /**
     * ActionHistory 데이터를 생성하기 전, 부모 테이블인 UserLastAction 초기화
     */
    @Test
    @Order(1)
    // 테스트 결과를 실제 DB에 반영
    void initializeUserLastAction() {
        System.out.println("=== 데이터 삽입 시작 ===");
        String sql = "INSERT INTO test.user_last_action (user_id, last_action_time) VALUES (?, ?)";

        List<Object[]> batchArgs = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (long i = 1; i <= USER_COUNT; i++) {
            batchArgs.add(new Object[]{i, now});
            if (batchArgs.size() >= BULK_INSERT_SIZE) {
                jdbcTemplate.batchUpdate(sql, batchArgs);
                batchArgs.clear();
            }
        }

        // 남은 데이터 처리
        if (!batchArgs.isEmpty()) {
            jdbcTemplate.batchUpdate(sql, batchArgs);
        }
        System.out.println("=== 데이터 삽입 완료 ===");

    }

    /**
     * 멀티스레드처리로 사용하여 ActionHistory 테스트 데이터 삽입
     */
    @Test
    @Order(2)
    void initializeActionHistory() throws InterruptedException {
        System.out.println("=== 데이터 삽입 시작 ===");
        CountDownLatch latch = new CountDownLatch(EXECUTE_COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    insertActionHistoryBatch();
                } catch (Exception e) {
                    log.info("data: {}", e.toString());
                } finally {
                    latch.countDown();
                    // 로그를 너무 많이 남기지 않도록 1000번에 한 번씩만 출력
                    if (latch.getCount() % 1000 == 0) {
                        System.out.println("남은 루프수 : " + latch.getCount());
                    }
                }
            });
        }

        latch.await(); // 모든 작업이 완료될 때까지 대기
        executorService.shutdown();

        long endTime = System.currentTimeMillis();
        System.out.println("=== 데이터 완료 시작 ===");
        System.out.println("작업 시간 : " + (endTime - startTime) + "ms");
    }

    /**
     * 대용량 삽입 처리
     */
    private void insertActionHistoryBatch() {
        String sql = 
                "INSERT INTO test.action_history (id, user_id, action_type, action_target, action_count, action_value, action_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) ";

        List<Object[]> batchArgs = new ArrayList<>(BULK_INSERT_SIZE);
        for (int i = 0; i < BULK_INSERT_SIZE; i++) {
            batchArgs.add(new Object[]{
                    snowflakeIdGenerator.nextId(),
                    getRandomUserId(),
                    getRandomActionType(),
                    getRandomActionTarget(),
                    getRandomActionCount(),
                    getRandomActionValue(),
                    getRandomActionTime()
            });
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    // ================== 랜덤 데이터 생성 유틸리티 메서드 ==================

    private long getRandomUserId() {
        return ThreadLocalRandom.current().nextLong(1, USER_COUNT + 1);
    }

    private String getRandomActionType() {
        return ACTION_TYPES.get(ThreadLocalRandom.current().nextInt(ACTION_TYPES.size()));
    }

    private String getRandomActionTarget() {
        return ACTION_TARGETS.get(ThreadLocalRandom.current().nextInt(ACTION_TARGETS.size()));
    }

    private int getRandomActionCount() {
        return ThreadLocalRandom.current().nextInt(1, 11); // 1 ~ 10
    }

    private double getRandomActionValue() {
        return ThreadLocalRandom.current().nextDouble(1.0, 100.0); // 1.0 ~ 99.9...
    }

    private Timestamp getRandomActionTime() {
        // 최근 12개월 (365일) 이내의 랜덤한 시간 생성
        long twelveMonthsAgo = LocalDateTime.now().minusMonths(12).toEpochSecond(ZoneOffset.UTC);
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        long randomEpochSecond = ThreadLocalRandom.current().nextLong(twelveMonthsAgo, now);
        return Timestamp.from(LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC).toInstant(ZoneOffset.UTC));
    }
}