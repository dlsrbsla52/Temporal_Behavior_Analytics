package study.temporal_behavior_analytics.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import study.temporal_behavior_analytics.common.domain.MDCKey;
import study.temporal_behavior_analytics.common.generator.KeyGenerator;

@Aspect
@Component
@RequiredArgsConstructor
public class BatchScheduleRequestIdAop {

    private final KeyGenerator logKeyGenerator;


    @Pointcut(value = "@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledPointcut() {}

    @Before(value = "scheduledPointcut()")
    public void scheduledMDCInitBeforeAop(JoinPoint joinPoint) {
        MDC.put(MDCKey.TRX_ID.getKey(), logKeyGenerator.generate());
    }

    @After(value = "scheduledPointcut()")
    public void scheduledMDCReleaseAfterAop(JoinPoint joinPoint) {
        MDC.clear();
    }

}
