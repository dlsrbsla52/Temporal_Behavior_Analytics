package study.temporal_behavior_analytics.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.temporal_behavior_analytics.common.generator.Snowflake;

@Configuration
public class KeyGeneratorConfiguration {

    private static final int LOG_KEY_DEFAULT_SIZE = 10;


    /**
     * 분산 시스템 환경을 고려한 key Snowflake 제네레이터 등록 bean
     * @return Snowflake 
     */
    @Bean
    public Snowflake snowflake() {
        return new Snowflake();
    }

}
