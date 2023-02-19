package hello.proxy.pureproxy.concreteproxy;

import hello.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import hello.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import hello.proxy.pureproxy.concreteproxy.code.TImeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }

    @Test
    void addProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TImeProxy tImeProxy = new TImeProxy(concreteLogic);
        // ConcreteClient는 다형성에 의해 ConcreteLogic에 concreteLogic도 들어갈 수 있고 timeProxy도 들어갈 수 있음
        ConcreteClient client = new ConcreteClient(tImeProxy);
        client.execute();
    }
}
