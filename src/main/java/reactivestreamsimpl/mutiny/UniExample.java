package reactivestreamsimpl.mutiny;

import io.smallrye.mutiny.Uni;

/**
 * [main] INFO reactivestreamsimpl.mutiny.SimpleUniSubscriber - subscribe
 * [main] INFO reactivestreamsimpl.mutiny.SimpleUniSubscriber - item: 1
 */
public class UniExample {
    public static void main(String[] args) {
        getItems()
                .subscribe()
                .withSubscriber(new SimpleUniSubscriber<>(Integer.MAX_VALUE));

    }

    private static Uni<Integer> getItems() {
        return Uni.createFrom().item(1);
    }
}
