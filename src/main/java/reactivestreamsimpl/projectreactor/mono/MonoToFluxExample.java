package reactivestreamsimpl.projectreactor.mono;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.projectreactor.SimpleSubscriber;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Mono 를 Flux 로
 * Flux 입장에서 Mono 는 하나의 값을 가진 객체이기 때문에 onNext 시 통으로 전달받음
 *
 * [main] INFO reactivestreamsimpl.projectreactor.mono.MonoToFluxExample - start main
 * [main] INFO reactivestreamsimpl.projectreactor.SimpleSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.projectreactor.SimpleSubscriber - request: 2147483647
 * [main] INFO reactivestreamsimpl.projectreactor.SimpleSubscriber - item: [1, 2, 3, 4, 5]
 * [main] INFO reactivestreamsimpl.projectreactor.SimpleSubscriber - complete
 * [main] INFO reactivestreamsimpl.projectreactor.mono.MonoToFluxExample - end main
 */
@Slf4j
public class MonoToFluxExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        getItems().flux().subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
        Thread.sleep(1000);
    }

    private static Mono<List<Integer>> getItems() {
        return Mono.just(List.of(1, 2, 3, 4, 5));
    }
}
