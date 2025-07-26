package study.temporal_behavior_analytics.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RequestTimeCheckAop {

    @Pointcut(value = "@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledPointcut() {}

    @Pointcut(value = "@annotation(org.springframework.context.event.EventListener)")
    public void listenerPointcut() {}


    @Around(value = "scheduledPointcut()")
    public Object commonLoggingAop(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        log.info("["+ getClassName(joinPoint) + "::" + getMethodName(joinPoint) + "]" + Thread.currentThread() + " start");

        try {
            return joinPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            log.info("["+ getClassName(joinPoint) + "::" + getMethodName(joinPoint) + "]" + Thread.currentThread() + " end 걸린 시간 : " + (end-start) + "ms");
        }
    }

    private String getClassName(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getDeclaringClass().getSimpleName();
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getName();
    }
}
