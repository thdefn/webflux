package completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureRunAsyncExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        assert !future.isDone();

        Thread.sleep(1000);
        assert future.isDone();
        assert future.get() == null;
    }
}
