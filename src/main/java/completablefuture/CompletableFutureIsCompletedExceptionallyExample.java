package completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureIsCompletedExceptionallyExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var futureWithException = CompletableFuture.supplyAsync(() -> {
            return 1 / 0;
        });
        Thread.sleep(100);
        assert futureWithException.isDone();
        assert futureWithException.isCompletedExceptionally();
    }
}
