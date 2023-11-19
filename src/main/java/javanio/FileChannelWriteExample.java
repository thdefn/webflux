package javanio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/**
 * [main] INFO javanio.FileChannelWriteExample - start main
 * [main] INFO javanio.FileChannelWriteExample - result: 12
 * [main] INFO javanio.FileChannelWriteExample - end main
 */
@Slf4j
public class FileChannelWriteExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        // 1. 파일을 읽어옴
        var file = new File(FileChannelWriteExample.class
                .getClassLoader()
                .getResource("hello.txt")
                .getFile());

        // 2. fileChannel 을 WRITE 모드로 오픈함
        var mode = StandardOpenOption.WRITE;
        try (var fileChannel = FileChannel.open(file.toPath(), mode)) {
            // 3. HeapByteBuffer 생성
            var byteBuffer = ByteBuffer.wrap("hello world2".getBytes());
            // 4. fileChannel 에 write && fileChannel 은 파일에 write
            var result = fileChannel.write(byteBuffer);
            // 5. limit 을 포지션으로 설정 & 포지션을 0으로 바꿈
            log.info("result: {}", result);
        }

        log.info("end main");
    }
}
