package reactivestreams;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

/**
 * 1000ms 간격으로 계속 값을 넣는 publisher
 */
@Slf4j
public class SimpleHotPublisher implements Flow.Publisher<Integer> {
    private final ExecutorService publisherExecutor = Executors.newSingleThreadExecutor();
    private final Future<Void> task;
    private List<Integer> numbers = new ArrayList<>();
    private List<SimpleHotSubscription> subscriptions = new ArrayList<>();

    public SimpleHotPublisher() {
        numbers.add(1);
        task = publisherExecutor.submit(() -> {
            for (int i = 2; !Thread.interrupted(); i++) {
                numbers.add(i);
                // 새로운 값이 추가되면 subscribers 에게 전파해야함 리스트 안에 subscriber 에게 wakeup 요청 보내짐
                subscriptions.forEach(SimpleHotSubscription::wakeup);
                Thread.sleep(5000);
            }
            return null;
        });
    }

    public void shutdown() {
        this.task.cancel(true);
        publisherExecutor.shutdown();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        var subscription = new SimpleHotSubscription(subscriber);
        subscriber.onSubscribe(subscription);
        subscriptions.add(subscription);
    }

    private class SimpleHotSubscription implements Flow.Subscription {
        private int offset; // 어디까지 전달했는지
        private int requiredOffset; // 나 이만큼이 필요해
        private final Flow.Subscriber<? super Integer> subscriber;
        private final ExecutorService subscriptionExecutorService = Executors.newSingleThreadExecutor();

        private SimpleHotSubscription(Flow.Subscriber<? super Integer> subscriber) {
            int lastElementIndex = numbers.size() - 1;
            this.offset = lastElementIndex;
            this.requiredOffset = lastElementIndex;
            this.subscriber = subscriber;
        }

        // n만큼 offset request
        @Override
        public void request(long n) {
            requiredOffset += n;
            onNextWhilePossible();
        }

        // publisher 의 wakeup 값이 추가되었으니 subscriber 에게 값을 전달해줄 수 있는 상황이면 전달해줘
        public void wakeup() {
            onNextWhilePossible();
        }

        @Override
        public void cancel() {
            this.subscriber.onComplete();
            if (subscriptions.contains(this)) {
                subscriptions.remove(this);
            }
            subscriptionExecutorService.shutdown();
        }

        // offset 을 늘려서 requiredOffset 까지 가야함
        private void onNextWhilePossible() {
            subscriptionExecutorService.submit(() -> {
                while (offset < requiredOffset && offset < numbers.size()) {
                    var item = numbers.get(offset);
                    subscriber.onNext(item); // 아이템 전달
                    offset++;
                }
            });
        }
    }
}
