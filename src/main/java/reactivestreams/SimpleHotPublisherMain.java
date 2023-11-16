package reactivestreams;

import lombok.SneakyThrows;

/**
 * [main] INFO reactivestreams.SimpleNamedSubscriber - onSubscribe
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 2
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 3
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 4
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 5
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 6
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 7
 * [main] INFO reactivestreams.SimpleNamedSubscriber - cancel
 * [main] INFO reactivestreams.SimpleNamedSubscriber - onComplete
 * [main] INFO reactivestreams.SimpleNamedSubscriber - onSubscribe
 * [pool-3-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 7
 * [main] INFO reactivestreams.SimpleNamedSubscriber - onSubscribe
 * [pool-4-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber3, onNext: 7
 * [pool-4-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber3, onNext: 8
 * [pool-3-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 8
 * [pool-3-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 9
 * [pool-4-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber3, onNext: 9
 * [pool-4-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber3, onNext: 10
 * [pool-3-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 10
 * [pool-3-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 11
 * [pool-4-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber3, onNext: 11
 * [main] INFO reactivestreams.SimpleNamedSubscriber - cancel
 * [main] INFO reactivestreams.SimpleNamedSubscriber - onComplete
 * [main] INFO reactivestreams.SimpleNamedSubscriber - cancel
 * [main] INFO reactivestreams.SimpleNamedSubscriber - onComplete
 * [main] INFO reactivestreams.SimpleNamedSubscriber - onSubscribe
 * [pool-5-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber4, onNext: 12
 * [pool-5-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber4, onNext: 13
 * [pool-5-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber4, onNext: 14
 * [pool-5-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber4, onNext: 15
 * [pool-5-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber4, onNext: 16
 * [pool-5-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber4, onNext: 17
 * [main] INFO reactivestreams.SimpleNamedSubscriber - cancel
 * [main] INFO reactivestreams.SimpleNamedSubscriber - onComplete
 */
public class SimpleHotPublisherMain {
    @SneakyThrows
    public static void main(String[] args) {
        // prepare publisher
        var publisher = new SimpleHotPublisher();

        // prepare subscriber1
        var subscriber = new SimpleNamedSubscriber<>("subscriber1");
        publisher.subscribe(subscriber);

        // cancel after 5s
        Thread.sleep(5000);
        subscriber.cancel();

        // prepare subscriber2,3
        var subscriber2 = new SimpleNamedSubscriber<>("subscriber2");
        var subscriber3 = new SimpleNamedSubscriber<>("subscriber3");
        publisher.subscribe(subscriber2);
        publisher.subscribe(subscriber3);

        // cancel after 5s
        Thread.sleep(5000);
        subscriber2.cancel();
        subscriber3.cancel();

        Thread.sleep(1000);

        var subscriber4 = new SimpleNamedSubscriber<>("subscriber4");
        publisher.subscribe(subscriber4);

        // cancel after 5s
        Thread.sleep(5000);
        subscriber4.cancel();

        // shutdown publisher
        publisher.shutdown();
    }
}
