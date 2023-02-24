package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 일반적인 스프링 빈 등록과정
 */
public class BasicTest {

    @Test
    void basicConfig() {
        ApplicationContext apllicationContext = new AnnotationConfigApplicationContext(BasicConfig.class); // 스프링 컨텍스트
        // A는 빈으로 등록함
        A a = apllicationContext.getBean("beanA", A.class);
        a.helloA();

        // B는 빈으로 등록되지 않음
        Assertions.assertThatThrownBy(() -> apllicationContext.getBean(B.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Slf4j
    @Configuration
    static class BasicConfig {

        @Bean(name = "beanA")
        public A a() {
            return new A();
        }
    }

    @Slf4j
    static class A {
        public void helloA() {
            log.info("hello A");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }
}





