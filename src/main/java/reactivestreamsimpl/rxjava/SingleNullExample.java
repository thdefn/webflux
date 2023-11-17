package reactivestreamsimpl.rxjava;

import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;

/**
 * [main] INFO reactivestreamsimpl.rxjava.SimpleSingleObserver - subscribe
 * [main] ERROR reactivestreamsimpl.rxjava.SimpleSingleObserver - error: onSuccess called with a null value. Null values are generally not allowed in 3.x operators and sources.
 */
@Slf4j
public class SingleNullExample {
    public static void main(String[] args) {
        getItem().subscribe(new SimpleSingleObserver<>());
    }
    private static Single<Integer> getItem(){
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(null);
        });
    }
}
