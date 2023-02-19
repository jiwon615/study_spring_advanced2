package hello.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

/**
 * TImeProxy 는 시간 측정하는 부가 기능 제공. 인터페이스가 아닌 클래스 ConcreteLogic 를 상속받음
 */
@Slf4j
public class TImeProxy extends ConcreteLogic{

    private ConcreteLogic realLogic;

    public TImeProxy(ConcreteLogic realLogic) {
        this.realLogic = realLogic;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();

        String result = realLogic.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeDecorator 종료 resultTime={}", resultTime); return result;
    }
}
