package reactivestreamsimpl.projectreactor.flux;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.projectreactor.SimpleSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 * [main] INFO reactivestreamsimpl.flux.FluxSimpleSubscribeOnExample - start main
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.flux.SimpleSubscriber - request: 2147483647
 * [main] INFO reactivestreamsimpl.flux.FluxSimpleSubscribeOnExample - end main
 * [single-1] INFO reactivestreamsimpl.flux.FluxSimpleSubscribeOnExample - map 1
 * [single-1] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 1
 * [single-1] INFO reactivestreamsimpl.flux.FluxSimpleSubscribeOnExample - map 2
 * [single-1] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 2
 * [single-1] INFO reactivestreamsimpl.flux.FluxSimpleSubscribeOnExample - map 3
 * [single-1] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 3
 * [single-1] INFO reactivestreamsimpl.flux.FluxSimpleSubscribeOnExample - map 4
 * [single-1] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 4
 * [single-1] INFO reactivestreamsimpl.flux.FluxSimpleSubscribeOnExample - map 5
 * [single-1] INFO reactivestreamsimpl.flux.SimpleSubscriber - item: 5
 * [single-1] INFO reactivestreamsimpl.flux.SimpleSubscriber - complete
 */
@Slf4j
public class FluxSimpleSubscribeOnExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        getItems()
                .map(i -> {
                    log.info("map {}", i);
                    return i;
                })
                .subscribeOn(Schedulers.single())
                .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
        Thread.sleep(1000);
    }

    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
