package reactivestreamsimpl.projectreactor.mono;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.SimpleSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Mono 를 Flux 로
 * Flux 의 fromIterable()에서 Iterable 객체를 풀어서 Flux 형태로 변환
 * Mono의 flatMapMany()는 이 결과로 나온 Flux 객체를 반환
 *
 * [main] INFO reactivestreamsimpl.projectreactor.mono.ListMonoToFluxExample - start main
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - request: 2147483647
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 1
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 2
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 3
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 4
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - item: 5
 * [main] INFO reactivestreamsimpl.SimpleSubscriber - complete
 * [main] INFO reactivestreamsimpl.projectreactor.mono.ListMonoToFluxExample - end main
 */
@Slf4j
public class ListMonoToFluxExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        getItems()
                .flatMapMany(Flux::fromIterable)
                .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
    }

    private static Mono<List<Integer>> getItems() {
        return Mono.just(List.of(1, 2, 3, 4, 5));
    }
}
