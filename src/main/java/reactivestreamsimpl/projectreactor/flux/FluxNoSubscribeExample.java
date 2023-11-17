package reactivestreamsimpl.projectreactor.flux;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * subscribe 하지 않으면 아무 일도 일어나지 않음
 *
 * [main] INFO reactivestreamsimpl.flux.FluxNoSubscribeExample - start main
 * [main] INFO reactivestreamsimpl.flux.FluxNoSubscribeExample - end main
 */
@Slf4j
public class FluxNoSubscribeExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems();
        log.info("end main");
    }

    private static Flux<Integer> getItems() {
        return Flux.create(integerFluxSink -> {
            log.info("start getItems");
            for (int i = 0; i < 5; i++) {
                integerFluxSink.next(i);
            }
            integerFluxSink.complete();
            log.info("end getItems");
        });
    }
}
