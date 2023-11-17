package reactivestreamsimpl.projectreactor.flux;

import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.projectreactor.SimpleSubscriber;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * [main] INFO reactivestreamsimpl.flux.FluxSimpleExample - start main
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - request: 2147483647
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 1
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 2
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 3
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 4
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 5
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - complete
 * [main] INFO reactivestreamsimpl.flux.FluxSimpleExample - end main
 */
@Slf4j
public class FluxSimpleExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems().subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
    }

    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
