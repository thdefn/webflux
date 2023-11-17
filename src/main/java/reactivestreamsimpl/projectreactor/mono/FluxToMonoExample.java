package reactivestreamsimpl.projectreactor.mono;

import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.SimpleSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Flux 를 Mono 로
 * 첫번째 값만 전달
 *
 * [main] INFO reactivestreamsimpl.projectreactor.mono.FluxToMonoExample - start main
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - request: 2147483647
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 1
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - complete
 * [main] INFO reactivestreamsimpl.projectreactor.mono.FluxToMonoExample - end main
 */
@Slf4j
public class FluxToMonoExample {
    public static void main(String[] args) {
        log.info("start main");
        Mono.from(getItems())
                        .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
    }

    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1,2,3,4,5));
    }
}
