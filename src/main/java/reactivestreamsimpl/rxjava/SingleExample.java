package reactivestreamsimpl.rxjava;

import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;

/**
 * [main] INFO reactivestreamsimpl.rxjava.SimpleSingleObserver - subscribe
 * [main] INFO reactivestreamsimpl.rxjava.SimpleSingleObserver - item: 1
 */
@Slf4j
public class SingleExample {
    public static void main(String[] args) {
        getItem().subscribe(new SimpleSingleObserver<>());
    }
    private static Single<Integer> getItem(){
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(1);
        });
    }
}
