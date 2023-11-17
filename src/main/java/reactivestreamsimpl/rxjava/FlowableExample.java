package reactivestreamsimpl.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.SimpleSubscriber;

import java.util.List;

/**
 * [main] INFO reactivestreamsimpl.rxjava.FlowableExample - start main
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - request: 2147483647
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 1
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 2
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 3
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 4
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 5
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - complete
 * [main] INFO reactivestreamsimpl.rxjava.FlowableExample - end main
 */
@Slf4j
public class FlowableExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems().subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
    }

    private static Flowable<Integer> getItems() {
        return Flowable.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
