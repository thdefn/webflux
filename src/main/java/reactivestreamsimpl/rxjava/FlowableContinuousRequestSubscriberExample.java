package reactivestreamsimpl.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.ContinuousRequestSubscriber;
import reactivestreamsimpl.SimpleSubscriber;

import java.util.List;

/**
 * [main] INFO reactivestreamsimpl.rxjava.FlowableContinuousRequestSubscriberExample - start main
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - item: 1
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - item: 2
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - item: 3
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - item: 4
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - item: 5
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - request: 1
 * [main] INFO reactivestreamsimpl.ContinuousRequestSubscriber - complete
 * [main] INFO reactivestreamsimpl.rxjava.FlowableContinuousRequestSubscriberExample - end main
 */
@Slf4j
public class FlowableContinuousRequestSubscriberExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems().subscribe(new ContinuousRequestSubscriber<>());
        log.info("end main");
    }

    private static Flowable<Integer> getItems() {
        return Flowable.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
