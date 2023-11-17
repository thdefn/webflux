package reactivestreamsimpl.mutiny;

import io.smallrye.mutiny.subscription.MultiSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;

/**
 * reactive streams 를 지원하지 않는다.
 * But onNext onError onCompletion onSubscribe 라는 이벤트의 큰 갈래는 유지함
 */
@Slf4j
@RequiredArgsConstructor
public class SimpleMultiSubscriber<T> implements MultiSubscriber<T> {
    private final Integer count;

    @Override
    public void onItem(T item) {
        log.info("item: {}", item);
    }

    @Override
    public void onFailure(Throwable failure) {
        log.error("fail: {}", failure.getMessage());
    }

    @Override
    public void onCompletion() {
        log.info("completion");
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(count);
        log.info("subscribe");
    }
}
