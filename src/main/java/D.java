import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Caller 는 Callee 를 호출한 후, 바로 자기의 일을 할 수 있다.
 * Caller 는 Callee 의 결과에 관심이 없다.
 */
@Slf4j
public class D {
    public static void main(String[] args) {
        log.info("Start main");
        getResult(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                var result = integer + 1;
                assert result == 1;
            }
        });
        log.info("Finish main");
    }

    public static void getResult(Consumer<Integer> cb) {
        var executor = Executors.newSingleThreadExecutor();
        try {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    log.info("Start getResult");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    var result = 0;
                    try {
                        cb.accept(result);
                    } finally {
                        log.info("Finish getResult");
                    }
                }
            });
        } finally {
            executor.shutdown();
        }
    }
}
