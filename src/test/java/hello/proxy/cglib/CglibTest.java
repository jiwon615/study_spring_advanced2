package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    @DisplayName("CGLIB 사용 테스트 코드")
    void cglib() {
        ConcreteService target = new ConcreteService();
        Enhancer enhancer = new Enhancer(); // CGLIB는 스프링의 Enhancer 를 사용해서 프록시를 생성한다.
        enhancer.setSuperclass(ConcreteService.class); // CGLIB는 구체 클래스를 상속 받아서 프록시를 생성할 수 있다. 어떤 구체 클래스를 상속 받을지 지정
        enhancer.setCallback(new TimeMethodInterceptor(target)); // 프록시에 적용할 실행 로직을 할당함
        ConcreteService proxy = (ConcreteService) enhancer.create(); // 프록시를 생성
        log.info("targetClass={}", target.getClass());  // 결과: class hello.proxy.common.ConcreteService
        log.info("proxyClass={}", proxy.getClass()); // 결과: class hello.proxy.common.ConcreteService$$EnhancerByCGLIB$$1c94e8ce

        proxy.call();
    }
}
