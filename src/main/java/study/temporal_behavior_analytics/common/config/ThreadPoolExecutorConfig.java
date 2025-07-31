package study.temporal_behavior_analytics.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolExecutorConfig {

    /**
     * 비동기 처리될 때 사용하는 Thread Executor
     *
     * @return Executor ThreadPoolTaskExecutor()
     */
    @Bean
    public Executor asycnExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(100);
        executor.setThreadNamePrefix("pushCompletedExecutor-");
        executor.setTaskDecorator(new MDCCopyTaskDecorator());
        return executor;
    }

    /**
     * 버츄얼 쓰레드를 사용하는 Thread Executor
     *
     * @return Executor VirtualThreadPerTaskExecutor()
     */
    @Bean
    public Executor vrThreadExecutor() {
        TaskExecutorAdapter adapter = new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
        adapter.setTaskDecorator(new MDCCopyTaskDecorator());
        return adapter;
    }
}
