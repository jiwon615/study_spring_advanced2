package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@Aspect //  애노테이션 기반 프록시를 적용할 때 필요
public class LogTraceAspect {
    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // Around 의 값에 포인트컷 표현식을 넣는다. 표현식은 AspectJ 표현식을 사용
    // @Around의 메소드는 어드바이스가 됨
    @Around("execution(* hello.proxy.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        TraceStatus status = null;
        // log.info("target={}", joinPoint.getTarget()); // 실제 호출 대상
        // log.info("getArgs={}", joinPoint.getArgs()); // 전달인자
        // log.info("getSignature={}", joinPoint.getSignature()); // join point
        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // 로직 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e; }
    }
}
