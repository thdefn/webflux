package completablefuture.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Future 인터페이스
 * - 비동기적인 작업을 수행한다
 * - 해당 작업이 완료되면 결과를 반환하는 인터페이스이다.
 * -- get() 으로 반환된 결과를 직접 이용한다는 점은 비동기적이다.
 *
 * Executors submit() 은 Callable 콜백 함수를 인자로 받아 Future 을 반환한다.
 */
public class FutureHelper {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future future = getFuture();
        assert !future.isDone();
        assert !future.isCancelled();

        var result = future.get();
        assert result.equals(1);
        assert future.isDone();
        assert !future.isCancelled();
    }

    public static Future<Integer> getFuture() {
        var executor = Executors.newSingleThreadExecutor();
        try {
            return executor.submit(() -> {
                return 1;
            });
        } finally {
            executor.shutdown();
        }
    }

    public static Future<Integer> getFutureCompleteAfter1s() {
        var executor = Executors.newSingleThreadExecutor();
        try {
            return executor.submit(() -> {
                Thread.sleep(1000);
                return 1;
            });
        } finally {
            executor.shutdown();
        }
    }
}
