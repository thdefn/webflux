package reactivestreams;

import lombok.SneakyThrows;

import java.util.concurrent.Flow;

/**
 * [pool-1-thread-1] INFO reactivestreams.RequestNSubscriber - item: FixedIntPublisher.Result(value=1, requestCount=1)
 * [pool-1-thread-1] INFO reactivestreams.RequestNSubscriber - send request
 * [pool-1-thread-1] INFO reactivestreams.RequestNSubscriber - item: FixedIntPublisher.Result(value=2, requestCount=2)
 * [pool-1-thread-1] INFO reactivestreams.RequestNSubscriber - item: FixedIntPublisher.Result(value=3, requestCount=2)
 * [pool-1-thread-1] INFO reactivestreams.RequestNSubscriber - item: FixedIntPublisher.Result(value=4, requestCount=2)
 * [pool-1-thread-1] INFO reactivestreams.RequestNSubscriber - send request
 * [pool-1-thread-1] INFO reactivestreams.RequestNSubscriber - item: FixedIntPublisher.Result(value=5, requestCount=3)
 * [pool-1-thread-1] INFO reactivestreams.RequestNSubscriber - item: FixedIntPublisher.Result(value=6, requestCount=3)
 * [pool-1-thread-1] INFO reactivestreams.RequestNSubscriber - item: FixedIntPublisher.Result(value=7, requestCount=3)
 * [pool-1-thread-1] INFO reactivestreams.RequestNSubscriber - send request
 * [pool-1-thread-1] INFO reactivestreams.RequestNSubscriber - complete
 */
public class FixedIntPublisherExample {
    @SneakyThrows
    public static void main(String[] args) {
        Flow.Publisher publisher = new FixedIntPublisher();
        Flow.Subscriber subscriber = new RequestNSubscriber(3);
        publisher.subscribe(subscriber);

        Thread.sleep(100);
    }
}
