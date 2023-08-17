import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;

/**
 * 모든 then*Async 연산자는 executor 을 추가 인자로 받는다
 */
@Slf4j
public class CompletionStageThenAsyncExecutorExample {
    public static void main(String[] args) throws InterruptedException {
        var single = Executors.newSingleThreadExecutor();
        var fixed = Executors.newFixedThreadPool(10);

        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStage();
        stage.thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync", i);
        }, fixed).thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync2", i);
        }, single);

        log.info("after thenAcceptAsync");
        Thread.sleep(200);
    }
}
