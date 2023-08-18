package completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

/**
 * thenCompose
 * - Function을 파라미터로 받는다.
 * - 이전 task 로부터 T 타입의 값을 받아서 가공하고 U 타입의 CompletionStage 를 반환한다.
 * - CompletionStage 가 done 상태가 되면 값을 다음 task 에 전달한다
 * - 다음 future 을 반환해야 하는 경우 사용한다
 *
 * 19:14:48 [main] - start main
 * 19:14:48 [ForkJoinPool.commonPool-worker-19] - I'm running!
 * 19:14:48 [ForkJoinPool.commonPool-worker-19] - in thenComposeAsync:
 * java.util.concurrent.CompletableFuture@37b05857[Not completed]
 * 19:14:48 [ForkJoinPool.commonPool-worker-19] - in thenComposeAsync2:
 * java.util.concurrent.CompletableFuture@6398d2c5[Not completed]
 * 19:14:48 [ForkJoinPool.commonPool-worker-5] - result: 2 in thenAcceptAsync
 */
@Slf4j
public class CompletionStageThenComposeAsyncExample {
    public static void main(String[] args) throws InterruptedException {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStage();
        stage.thenComposeAsync(value -> {
            var next = Helper.addOne(value);
            log.info("in thenComposeAsync: {}", next);
            return next;
        }).thenComposeAsync(value -> {
            var next = Helper.addResultPrefix(value);
            log.info("in thenComposeAsync2: {}", next);
            return next;
        }).thenAcceptAsync(value -> {
            log.info("{} in thenAsync", value);
        });
        Thread.sleep(1000);
    }
}
