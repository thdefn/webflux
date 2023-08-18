package completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

/**
 * thenApply
 * - Function 을 파라미터로 받는다
 * - 이전 task 로부터 값을 받아 가공해서 반환한다.
 * - 다음 task 에게 반환한 값이 전달된다.
 *
 * [ForkJoinPool.commonPool-worker-19] - return in future
 * [ForkJoinPool.commonPool-worker-19] - in thenApplyAsync: 2
 * [ForkJoinPool.commonPool-worker-19] - in thenApplyAsync2: result: 2
 * [ForkJoinPool.commonPool-worker-19] - in thenApplyAsync3: true
 * [ForkJoinPool.commonPool-worker-19] - true
 */
@Slf4j
public class CompletionStageThenApplyAsyncExample {
    public static void main(String[] args) {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStage();
        stage.thenApplyAsync(value ->{
            var next = value +1;
            return next;
        }).thenApplyAsync(value ->{
            var next = "result: " + value;
            return next;
        }).thenApplyAsync(value -> {
            var next = value.equals("result: 2");
            return next;
        }).thenAcceptAsync(value -> log.info("{}", value));
    }
}
