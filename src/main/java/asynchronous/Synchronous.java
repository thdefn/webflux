package asynchronous;

import lombok.extern.slf4j.Slf4j;

/**
 * Caller 는 Callee 의 결과에 관심이 있다.
 */
@Slf4j
public class Synchronous {
    public static void main(String[] args) {
        log.info("Start main");
        var result = getResult();
        var nextValue = result + 1;
        assert nextValue == 1;
        log.info("Finish main");
    }

    public static int getResult() {
        log.info("Start getResult");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var result = 0;
        try {
            return result;
        } finally {
            log.info("Finish getResult");
        }
    }
}
