package study.temporal_behavior_analytics.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.temporal_behavior_analytics.common.generator.KeyGenerator;
import study.temporal_behavior_analytics.common.generator.UUIDKeyGenerator;

@Configuration
public class KeyGeneratorConfiguration {

    private static final int LOG_KEY_DEFAULT_SIZE = 10;

    @Bean
    public KeyGenerator logKeyGenerator() {
        UUIDKeyGenerator generator = new UUIDKeyGenerator();
        generator.setHyphen(false);
        generator.setKeySizePolicy(LOG_KEY_DEFAULT_SIZE);
        return generator;
    }
}
