package completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

/**
 * 55:36 [ForkJoinPool.commonPool-worker-19] - return in future
 * 55:36 [ForkJoinPool.commonPool-worker-5] - in thenApplyAsync
 * 55:36 [ForkJoinPool.commonPool-worker-5] -
 * java.lang.ArithmeticException: / by zero in exceptionally
 * 55:36 [ForkJoinPool.commonPool-worker-5] - 0 in thenAcceptAsync
 */
@Slf4j
public class CompletionStageExceptionallyExample {
    public static void main(String[] args) throws InterruptedException {
        Helper.completionStage()
                .thenApplyAsync(i -> {
                    log.info("in thenAsync");
                    return i/0;
                }).exceptionally(e -> {
                    log.info("{} in exceptionally", e.getMessage());
                    return 0;
                }).thenAcceptAsync(value -> {
                    log.info("{} in thenAcceptAsync", value);
                });

        Thread.sleep(1000);
    }
}
