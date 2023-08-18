package asynchronous;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Caller 는 Callee 를 호출한 후, 바로 자기의 일을 할 수 있다.
 * Caller 는 Callee 의 결과에 관심이 있다.
 */
@Slf4j
public class NonBlockingSynchronous {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        log.info("Start main");
        Future<Integer> result = getResult();
        var count = 1;
        while (!result.isDone()){
            log.info("Waiting for result, count : {}", count++);
            Thread.sleep(100);
        }

        var nextValue = result.get() + 1;
        assert nextValue == 1;
        log.info("Finish main");
    }

    public static Future getResult() {
        var executor = Executors.newSingleThreadExecutor();
        try {
            return executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    log.info("Start getResult");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    var result = 0;
                    try {
                        return result;
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
