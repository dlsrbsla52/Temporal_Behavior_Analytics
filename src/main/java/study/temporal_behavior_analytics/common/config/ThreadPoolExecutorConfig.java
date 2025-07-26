package study.temporal_behavior_analytics.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolExecutorConfig {

    @Bean
    public Executor pushCompletedExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("pushCompletedExecutor-");
        executor.setTaskDecorator(new MDCCopyTaskDecorator());
        return executor;
    }

    @Bean
    public Executor loggingExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("LoggingExecutor-");
        executor.setTaskDecorator(new MDCCopyTaskDecorator());
        return executor;
    }

    @Bean
    public Executor pushSendExecutor() {
        TaskExecutorAdapter adapter = new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
        adapter.setTaskDecorator(new MDCCopyTaskDecorator());
        return adapter;
    }
}
