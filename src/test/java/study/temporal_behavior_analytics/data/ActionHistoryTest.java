package study.temporal_behavior_analytics.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import study.temporal_behavior_analytics.user.tracking.entity.ActionHistory;
import study.temporal_behavior_analytics.user.tracking.repository.ActionHistoryRepository;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
@ActiveProfiles("local")
public class ActionHistoryTest {
    
    @Autowired
    private ActionHistoryRepository actionHistoryRepository;
    
    @Test
    void InitTest(){
        log.info("snowFlakeId 데이터 init 테스트");

        ActionHistory save = actionHistoryRepository.save(
                new ActionHistory(
                        999999L, "VIEW", "product_1", 1, 100.0, LocalDateTime.now()
                )
        );
        log.info("save: {}", save);
    }
}
