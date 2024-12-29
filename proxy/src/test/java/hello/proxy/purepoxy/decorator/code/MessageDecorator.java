package hello.proxy.purepoxy.decorator.code;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component{

    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("메시지 데코레이터 실행");
        String result = component.operation();
        String decoResult = "*****" + result + "*****";
        log.info("데코레이터 적용 전={}, 후={}", result, decoResult);
        return decoResult;
    }
}
