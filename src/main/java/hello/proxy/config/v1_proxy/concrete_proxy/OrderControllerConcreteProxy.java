package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderControllerConcreteProxy extends OrderControllerV2 {

    private final OrderControllerV2 target;
    private final LogTrace logTrace;

    public OrderControllerConcreteProxy(OrderControllerV2 target, LogTrace logTrace) {
        super(null); // 자식 클래스를 생성할 때는 항상 super() 로 부모 클래스의 생성자를 호출해야 한다
        this.target = target;
        this.logTrace = logTrace;
    }
}
