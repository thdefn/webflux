package reactivestreamsimpl.projectreactor.mono;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.projectreactor.SimpleSubscriber;
import reactor.core.publisher.Mono;

/**
 * [main] INFO reactivestreamsimpl.projectreactor.mono.MonoSimpleExample - start main
 * [main] INFO reactivestreamsimpl.projectreactor.SimpleSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.projectreactor.SimpleSubscriber - request: 2147483647
 * [main] INFO reactivestreamsimpl.projectreactor.SimpleSubscriber - item: 1
 * [main] INFO reactivestreamsimpl.projectreactor.SimpleSubscriber - complete
 * [main] INFO reactivestreamsimpl.projectreactor.mono.MonoSimpleExample - end main
 */
@Slf4j
public class MonoSimpleExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        getItems().subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
        Thread.sleep(1000);
    }

    private static Mono<Integer> getItems(){
        return Mono.create(integerMonoSink -> {
            integerMonoSink.success(1);
        });
    }
}
