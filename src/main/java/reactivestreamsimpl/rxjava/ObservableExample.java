package reactivestreamsimpl.rxjava;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * [main] INFO reactivestreamsimpl.rxjava.ObservableExample - start main
 * [main] INFO reactivestreamsimpl.rxjava.SimpleObserver - subscribe
 * [main] INFO reactivestreamsimpl.rxjava.SimpleObserver - item: 1
 * [main] INFO reactivestreamsimpl.rxjava.SimpleObserver - item: 2
 * [main] INFO reactivestreamsimpl.rxjava.SimpleObserver - item: 3
 * [main] INFO reactivestreamsimpl.rxjava.SimpleObserver - item: 4
 * [main] INFO reactivestreamsimpl.rxjava.SimpleObserver - item: 5
 * [main] INFO reactivestreamsimpl.rxjava.SimpleObserver - complete
 * [main] INFO reactivestreamsimpl.rxjava.ObservableExample - end main
 */
@Slf4j
public class ObservableExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems().subscribe(new SimpleObserver());
        log.info("end main");
    }

    private static Observable<Integer> getItems() {
        return Observable.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
