package reactivestreamsimpl.projectreactor.flux;

import lombok.extern.slf4j.Slf4j;
import reactivestreamsimpl.SimpleSubscriber;
import reactor.core.publisher.Flux;

/**
 * Publisher Flux 는 에러를 전달하면 Flux 가 더 이상 진행되지 않음
 */
@Slf4j
public class FluxErrorExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems().subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
    }

    public static Flux<Integer> getItems() {
        return Flux.create(integerFluxSink -> {
            integerFluxSink.next(0);
            integerFluxSink.next(1);
            var error = new RuntimeException("error in flux");
            integerFluxSink.error(error);
        });
    }
}
