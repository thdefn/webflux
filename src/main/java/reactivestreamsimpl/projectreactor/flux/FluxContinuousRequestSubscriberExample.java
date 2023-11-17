package reactivestreamsimpl.projectreactor.flux;

import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.ContinuousRequestSubscriber;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * back pressure - Subscriber 가 필요한 만큼의 처리를 할 수 있음
 *
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - item: 1
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - item: 2
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - item: 3
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - item: 4
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - item: 5
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.flux.ContinuousRequestSubscriber - complete
 */
@Slf4j
public class FluxContinuousRequestSubscriberExample {
    public static void main(String[] args) {
        getItems().subscribe(new ContinuousRequestSubscriber<>());
    }
    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
