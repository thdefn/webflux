package completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureSupplyAsyncExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        });
        assert !future.isDone();

        Thread.sleep(1000);
        assert future.isDone();
        assert future.get() == 1;
    }
}
