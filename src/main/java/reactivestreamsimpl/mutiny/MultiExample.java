package reactivestreamsimpl.mutiny;

import io.smallrye.mutiny.Multi;

/**
 * [main] INFO reactivestreamsimpl.mutiny.SimpleMultiSubscriber - item: 1
 * [main] INFO reactivestreamsimpl.mutiny.SimpleMultiSubscriber - item: 2
 * [main] INFO reactivestreamsimpl.mutiny.SimpleMultiSubscriber - item: 3
 * [main] INFO reactivestreamsimpl.mutiny.SimpleMultiSubscriber - item: 4
 * [main] INFO reactivestreamsimpl.mutiny.SimpleMultiSubscriber - item: 5
 * [main] INFO reactivestreamsimpl.mutiny.SimpleMultiSubscriber - completion
 * [main] INFO reactivestreamsimpl.mutiny.SimpleMultiSubscriber - subscribe
 */
public class MultiExample {
    public static void main(String[] args) {
        getItems()
                .subscribe()
                .withSubscriber(new SimpleMultiSubscriber<>(Integer.MAX_VALUE));
    }

    private static Multi<Integer> getItems() {
        return Multi.createFrom().items(1, 2, 3, 4, 5);
    }
}
