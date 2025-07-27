package study.temporal_behavior_analytics.common.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;
import study.temporal_behavior_analytics.common.generator.Snowflake;

/**
 * Snowflake 알고리즘을 사용하여 엔티티의 ID를 생성하는 커스텀 ID
 */
@Component
@RequiredArgsConstructor
public class SnowflakeIdGenerator implements IdentifierGenerator {

    private final Snowflake snowflake;

    /**
     * 새로운 ID 할당
     * Hibernate 세션과 관계없이 새로운 Snowflake ID를 생성하여 반환.
     * JdbcTemplate 등 외부에서 ID가 필요할 때 이 메서드를 직접 호출 해야한다.
     * @return 생성된 Long 타입의 ID
     */
    public long nextId() {
        return snowflake.nextId();
    }

    /**
     * 새로운 ID 할당 JAP Entity가 호출될 때 사용한다.
     * @param session 현재 세션
     * @param object ID가 필요한 엔티티 객체
     * @return 생성된 Long 타입의 ID
     */
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return snowflake.nextId();
    }
}

