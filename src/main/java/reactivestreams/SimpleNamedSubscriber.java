package reactivestreams;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;

@Slf4j
public class SimpleNamedSubscriber<T> implements Flow.Subscriber<T> {
    private Flow.Subscription subscription;
    private final String name;

    public SimpleNamedSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
        log.info("onSubscribe");
    }

    @Override
    public void onNext(T item) {
        log.info("name: {}, onNext: {}", name, item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("onError: {}", throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
    }

    public void cancel(){
        log.info("cancel");
        subscription.cancel();
    }
}
