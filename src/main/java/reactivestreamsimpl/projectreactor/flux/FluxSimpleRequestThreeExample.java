package reactivestreamsimpl.projectreactor.flux;

import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.projectreactor.SimpleSubscriber;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * [main] INFO reactivestreamsimpl.flux.FluxSimpleRequestThreeExample - start main
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - request: 3
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 1
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 2
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 3
 * [main] INFO reactivestreamsimpl.flux.FluxSimpleRequestThreeExample - end main
 */
@Slf4j
public class FluxSimpleRequestThreeExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems().subscribe(new SimpleSubscriber<>(3));
        log.info("end main");
    }

    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
