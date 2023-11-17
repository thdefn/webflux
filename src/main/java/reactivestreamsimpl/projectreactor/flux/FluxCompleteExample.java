package reactivestreamsimpl.projectreactor.flux;

import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.projectreactor.SimpleSubscriber;
import reactor.core.publisher.Flux;

/**
 * Flux 는 아무 값도 넘기지 않은 상황에서 complete 할 수 있다.
 *
 * [main] INFO reactivestreamsimpl.projectreactor.flux.FluxCompleteExample - start main
 * [main] INFO reactivestreamsimpl.projectreactor.SimpleSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.projectreactor.SimpleSubscriber - request: 2147483647
 * [main] INFO reactivestreamsimpl.projectreactor.SimpleSubscriber - complete
 * [main] INFO reactivestreamsimpl.projectreactor.flux.FluxCompleteExample - end main
 */
@Slf4j
public class FluxCompleteExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems().subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
    }

    public static Flux<Integer> getItems() {
        return Flux.create(integerFluxSink -> {
            integerFluxSink.complete();
        });
    }
}
