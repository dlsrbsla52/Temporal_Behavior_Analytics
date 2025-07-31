package study.temporal_behavior_analytics.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
public class BatchService {
    private final JdbcTemplate jdbcTemplate;

    /**
     * 매월 1일 새벽 1시에 다음 달의 파티션 테이블을 미리 생성
     */
    @Scheduled(cron = "0 0 1 1 * ?") // 
    public void createNextMonthPartition() {
        try {
            // 다음 달 날짜 계산
            LocalDate nextMonth = LocalDate.now().plusMonths(1);

            // 파티션 이름 및 기간 포맷팅
            // 예: 2025년 9월에 실행 -> tableName: action_history_2025_10
            String tableName = String.format("action_history_%d_%02d", nextMonth.getYear(), nextMonth.getMonthValue());

            // partition_start: 2025-10-01, partition_end: 2025-11-01
            String partitionStart = nextMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-01"));
            String partitionEnd = nextMonth.plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-01"));

            // 3. DDL 쿼리 생성
            String ddlQuery = String.format(
                    "CREATE TABLE IF NOT EXISTS %s PARTITION OF action_history FOR VALUES FROM ('%s') TO ('%s');",
                    tableName,
                    partitionStart,
                    partitionEnd
            );

            log.info("다음 달 파티션 생성 시도: {}", ddlQuery);

            // 4. DDL 쿼리 실행
            jdbcTemplate.execute(ddlQuery);

            log.info("성공적으로 파티션 테이블을 생성했습니다: {}", tableName);

        } catch (Exception e) {
            // 파티션이 이미 존재하거나 다른 DB 에러 발생 시 로깅
            log.error("파티션 테이블 생성 중 에러가 발생했습니다.", e);
        }
    }
}
