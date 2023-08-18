package completablefuture.completionstage;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

/**
 * thenAccept
 * - Consumer 를 파라미터로 받는다
 * - 이전 task 로부터 값을 받지만 다음 task 에게 null 이 전달된다
 * - 값을 받아서 액션만 수행하는 경우 유용
 * <p>
 * - 쓰레드 풀에 있는 쓰레드에서 action을 실행한다.
 * <p>
 * [main] - start main
 * [ForkJoinPool.commonPool-worker-19] - return in future
 * [main] - after thenAccept
 * [ForkJoinPool.commonPool-worker-19] - 1 in thenAcceptAsync
 * [ForkJoinPool.commonPool-worker-5] - null in thenAcceptAsync2
 */
@Slf4j
public class CompletionStageThenAcceptAsyncExample {
    public static void main(String[] args) {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.finishedStage();
        stage.thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync", i);
        }).thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync2", i);
        });
        log.info("after thenAcceptAsync");
    }
}
