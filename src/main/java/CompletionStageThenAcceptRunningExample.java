import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

/**
 * thenAccept
 * - Consumer 를 파라미터로 받는다
 * - 이전 task 로부터 값을 받지만 다음 task 에게 null 이 전달된다
 * - 값을 받아서 액션만 수행하는 경우 유용
 *
 * - thenAccept를 호출한 쓰레드에서 action을 실행한다.
 * -> 여기서는 done 상태가 아니기 때문에, callee 인 folkJoinPool 의 쓰레드에서 실행된다
 */
@Slf4j
public class CompletionStageThenAcceptRunningExample {
    public static void main(String[] args) throws InterruptedException {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.runningStage();
        stage.thenAccept(i -> {
            log.info("{} in thenAccept", i);
        }).thenAccept(i -> {
            log.info("{} in thenAccept2", i);
        });

        // FolkJoinPool은 메인 쓰레드가 살아 있어야 한다.
        Thread.sleep(2000);
    }
}
