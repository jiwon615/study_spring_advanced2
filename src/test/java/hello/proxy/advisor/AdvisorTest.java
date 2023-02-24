package hello.proxy.advisor;

import hello.proxy.common.ServiceImpl;
import hello.proxy.common.ServiceInterface;
import hello.proxy.common.advice.TimeAdvice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class AdvisorTest {

    @Test
    @DisplayName("어드바이저 지정하여 ProxyFactory 사용")
    void advisorTest1() {
        ServiceImpl target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // new DefaultPointcutAdvisor: Advisor 인터페이스의 가장 일반적인 구현
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice()); // 포인트컷, 어드바이스 지정
        proxyFactory.addAdvisor(advisor); // 프록시팩토리에 적용할 어드바이저 지정
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트컷 적용하여 ProxyFactory 사용")
    void advisorTest2() {
        ServiceImpl target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 직접 만든 MyPointCut()을 지정
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointCut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save(); // 어드바이스 적용되는 결과 나옴
        proxy.find();  // 직접 만든 메소드필터링 하는 포인트컷때문에 어드바이스가 적용되지 않음
    }

    static class MyPointCut implements Pointcut {
        @Override
        public ClassFilter getClassFilter() { // 클래스가 맞는지 핉터링
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() { // 메소드가 맞는지 필터링
            return new MyMethodMatcher();
        }
    }

    // save라는 메소드만 어드바이스 적용될 수 있도록 필터링 기능 하도록, Custom MethodMatcher를 직접 구현
    static class MyMethodMatcher implements MethodMatcher {
        private String matchName = "save";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = method.getName().equals(matchName);
            log.info("포인트컷 호출 method={}", method.getName());
            log.info("포인트컷 결과 result={}", result);
            return result;
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3() {
        ServiceImpl target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut(); // 스프링이 무수히 많은 포인트컷 제공 하며, NameMatchMethodPointcut은 그 중 하나임
        pointcut.setMappedName("save"); // NameMatchMethodPointcut 생성 후 사용한 메소드 이름 지정하면 포인트컷 완성됨
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }
}
