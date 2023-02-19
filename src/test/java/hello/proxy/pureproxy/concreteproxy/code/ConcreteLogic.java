package hello.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

// 구체 클래스 기반 프록시 (인터페이스가 없는 구체클래스인 상황) 여기에 프록시를 도입해야 함
@Slf4j
public class ConcreteLogic {

    public String operation() {
        log.info("ConcreteLogic 실행");
        return "data";
    }
}
