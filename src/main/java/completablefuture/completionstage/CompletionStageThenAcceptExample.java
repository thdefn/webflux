package completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

/**
 * thenAccept
 * - Consumer 를 파라미터로 받는다
 * - 이전 task 로부터 값을 받지만 다음 task 에게 null 이 전달된다
 * - 값을 받아서 액션만 수행하는 경우 유용
 *
 * - thenAccept를 호출한 쓰레드에서 action을 실행한다.
 */
@Slf4j
public class CompletionStageThenAcceptExample {
    public static void main(String[] args) {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.finishedStage();
        stage.thenAccept(i -> {
            log.info("{} in thenAccept", i);
        }).thenAccept(i -> {
            log.info("{} in thenAccept2", i);
        });
        log.info("after thenAccept");
    }
}
