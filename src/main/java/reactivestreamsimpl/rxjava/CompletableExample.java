package reactivestreamsimpl.rxjava;

import io.reactivex.rxjava3.core.Completable;
import lombok.extern.slf4j.Slf4j;

/**
 * [main] INFO reactivestreamsimpl.rxjava.SimpleCompletableObserver - subscribe
 * [main] INFO reactivestreamsimpl.rxjava.SimpleCompletableObserver - complete
 */
@Slf4j
public class CompletableExample {
    public static void main(String[] args) {
        getItem().subscribe(new SimpleCompletableObserver());
    }

    private static Completable getItem() {
        return Completable.create(completableEmitter -> {
            Thread.sleep(1000);
            completableEmitter.onComplete();
        });
    }
}
