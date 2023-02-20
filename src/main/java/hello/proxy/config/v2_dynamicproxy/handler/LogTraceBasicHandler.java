package hello.proxy.config.v2_dynamicproxy.handler;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 해당 클래스는 JDK 동적 프록시에 적용할 로직으로, InvocationHandler 인터페이스를 구현한다.
 * - v1/no-log인 경우에도 LogTraceBasicHandler를 타게 된다
 */
public class LogTraceBasicHandler implements InvocationHandler {

    private final Object target; // 동적 프록시가 호출할 대상
    private final LogTrace logTrace;

    public LogTraceBasicHandler(Object target, LogTrace logTrace) {
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TraceStatus status = null;
        try {

            // method 를 통해 메소드와 클래스 정보 동적 확인하여 LogTrace 에 사용할 메시지 지정  ex. OrderController.request()
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = logTrace.begin(message);

            // 로직 호출
            Object result = method.invoke(target, args); // target 인스턴스의 메소드를 실행. args는 메소드 호출 시 넘겨줄 인수

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
