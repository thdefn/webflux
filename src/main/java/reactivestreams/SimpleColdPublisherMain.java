package reactivestreams;

import lombok.SneakyThrows;

/**
 * [main] INFO reactivestreams.SimpleNamedSubscriber - onSubscribe
 * [pool-1-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 1
 * [pool-1-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 2
 * [pool-1-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 3
 * [pool-1-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 4
 * [pool-1-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 5
 * [pool-1-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 6
 * [pool-1-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 7
 * [pool-1-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 8
 * [pool-1-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber1, onNext: 9
 * [pool-1-thread-1] INFO reactivestreams.SimpleNamedSubscriber - onComplete
 * [main] INFO reactivestreams.SimpleNamedSubscriber - onSubscribe
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 1
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 2
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 3
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 4
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 5
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 6
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 7
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 8
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - name: subscriber2, onNext: 9
 * [pool-2-thread-1] INFO reactivestreams.SimpleNamedSubscriber - onComplete
 */
public class SimpleColdPublisherMain {
    @SneakyThrows
    public static void main(String[] args) {
        // create publisher
        var publisher = new SimpleColdPublisher();

        // create subscriber1
        var subscriber = new SimpleNamedSubscriber<Integer>("subscriber1");
        publisher.subscribe(subscriber);

        Thread.sleep(5000);

        // create subscriber2
        var subscriber2 = new SimpleNamedSubscriber<Integer>("subscriber2");
        publisher.subscribe(subscriber2);
    }
}
