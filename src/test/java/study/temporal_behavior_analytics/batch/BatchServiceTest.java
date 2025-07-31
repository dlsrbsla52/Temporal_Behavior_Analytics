package study.temporal_behavior_analytics.batch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BatchService batchService;

    @Nested
    @DisplayName("createNextMonthPartition 메소드")
    class CreateNextMonthPartitionTest {

        @Test
        @DisplayName("다음 달 파티션 생성_유효한 값_성공")
        void createNextMonthPartition_유효한값_성공() {
            // given
            LocalDate nextMonth = LocalDate.now().plusMonths(1);
            String tableName = String.format("action_history_%d_%02d", nextMonth.getYear(), nextMonth.getMonthValue());
            String partitionStart = nextMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-01"));
            String partitionEnd = nextMonth.plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-01"));
            String expectedQuery = String.format(
                    "CREATE TABLE IF NOT EXISTS %s PARTITION OF action_history FOR VALUES FROM ('%s') TO ('%s');",
                    tableName,
                    partitionStart,
                    partitionEnd
            );

            // when
            batchService.createNextMonthPartition();

            // then
            verify(jdbcTemplate, times(1)).execute(expectedQuery);
        }

        @Test
        @DisplayName("다음 달 파티션 생성_JDBC 오류_예외 처리")
        void createNextMonthPartition_Jdbc오류_예외처리() {
            // given
            doThrow(new RuntimeException("JDBC Error")).when(jdbcTemplate).execute(anyString());

            // when & then
            assertDoesNotThrow(() -> batchService.createNextMonthPartition());
            verify(jdbcTemplate, times(1)).execute(anyString());
        }
    }
}