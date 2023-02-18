package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{

    private Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("프록시 호출");
        if (cacheValue == null) { // 처음만 실제 객체(target)을 호출해서 값을 구하고
            cacheValue = target.operation();
        }
        return cacheValue; // 값이 있으면 실제 객체 전혀 호출없이 캐시 값 그대로 반환 하므로 매우 빠르게 조회 가능
    }
}
