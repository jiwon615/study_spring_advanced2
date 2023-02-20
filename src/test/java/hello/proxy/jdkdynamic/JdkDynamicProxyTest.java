package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.jdkdynamic_code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        // 클래스 로더 정보, 인터페이스, 그리고 핸들러 로직을 넣으면 해당 인터페이스 기반으로 동적 프록시 생성하고 그 결과를 반환
        AInterface proxy = (AInterface) Proxy.newProxyInstance(
                AInterface.class.getClassLoader(),
                new Class[]{AInterface.class},
                handler);
        proxy.call(); // AInterface의 call() 메소드를 호출
        log.info("targetClass={}", target.getClass()); // class hello.proxy.jdkdynamic.jdkdynamic_code.AImpl
        log.info("proxyClass={}", proxy.getClass()); // 동적으로 생성된 프록시 클래스 장보 (출력결과 예: class com.sun.proxy.$Proxy12)
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);
        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }
}
