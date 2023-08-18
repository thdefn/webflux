package completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

/**
 * thenCompose
 * - Runnable 을 파라미터로 받는다
 * - 이전 task 로부터 값을 받지 않고 값을 반환하지 않는다,
 * - 다음 task 에게 null 이 전달된다
 * - future 가 완료되었다는 이벤트를 기록할 때 유용
 *
 * 48:32 [main] - start main
 * 48:32 [ForkJoinPool.commonPool-worker-19] - return in future
 * 48:32 [ForkJoinPool.commonPool-worker-19] - in thenRunAsync
 * 48:32 [ForkJoinPool.commonPool-worker-19] - in thenRunAsync2
 * 48:32 [ForkJoinPool.commonPool-worker-19] - null in thenAcceptAsync
 */
@Slf4j
public class CompletionStageThenRunAsyncExample {
    public static void main(String[] args) throws InterruptedException {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStage();
        stage.thenRunAsync(() -> {
            log.info("in theRunAsync");
        }).thenRunAsync(() -> {
            log.info("in theRunAsync");
        }).thenAcceptAsync(value -> {
            log.info("{} in thenAcceptAsync", value);
        });
        Thread.sleep(100);
    }
}
