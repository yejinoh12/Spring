package yejin.advanced.app.v5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yejin.advanced.trace.callback.TraceTemplate;
import yejin.advanced.trace.logtrace.LogTrace;
import yejin.advanced.trace.template.AbstractTemplate;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV5 {

    private final TraceTemplate traceTemplate;

    public void save(String itemId) {
        traceTemplate.execute("OrderRepository.save()", () -> {
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생");
            }
            sleep(1000);
            return null;
        });
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
