package completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

/**
 * thenAccept
 * - Consumer 를 파라미터로 받는다
 * - 이전 task 로부터 값을 받지만 다음 task 에게 null 이 전달된다
 * - 값을 받아서 액션만 수행하는 경우 유용
 *
 * - 쓰레드 풀에 있는 쓰레드에서 action을 실행한다.
 */
@Slf4j
public class CompletionStageThenAcceptAsyncRunningExample {
    public static void main(String[] args) throws InterruptedException {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.runningStage();
        stage.thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync", i);
        }).thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync2", i);
        });

        // FolkJoinPool은 메인 쓰레드가 살아 있어야 한다.
        Thread.sleep(2000);
    }
}
