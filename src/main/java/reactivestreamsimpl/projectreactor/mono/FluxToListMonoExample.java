package reactivestreamsimpl.projectreactor.mono;

import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.SimpleSubscriber;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * collectList() 일종의 subscriber
 * onNext [1,2,3,4,5] 받아 내부 배열에 저장한다
 * onComplete 가 내려오는 순간 가지고 있는 배열을 onNext 로 아래에다 전달함
 *
 * [main] INFO reactivestreamsimpl.projectreactor.mono.FluxToMonoExample - start main
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - request: 2147483647
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 1
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - complete
 * [main] INFO reactivestreamsimpl.projectreactor.mono.FluxToMonoExample - end main
 */
@Slf4j
public class FluxToListMonoExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems()
                .collectList()
                .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
    }

    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1,2,3,4,5));
    }
}
