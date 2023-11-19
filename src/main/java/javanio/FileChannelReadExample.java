package javanio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * [main] INFO javanio.FileChannelReadExample - start main
 * [main] INFO javanio.FileChannelReadExample - result: Hello world
 * [main] INFO javanio.FileChannelReadExample - end main
 */
@Slf4j
public class FileChannelReadExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        // 1. 파일을 읽어옴
        var file = new File(FileChannelReadExample.class
                .getClassLoader()
                .getResource("hello.txt")
                .getFile());

        // 2. fileChannel 을 오픈함
        try (var fileChannel = FileChannel.open(file.toPath())) {
            // 3. DirectByteBuffer 생성
            var byteBuffer = ByteBuffer.allocateDirect(1024);
            // 4. fileChannel 에 read & byteBuffer 에 fileChannel 이 쭉 write
            fileChannel.read(byteBuffer);
            // 5. limit 을 포지션으로 설정 & 포지션을 0으로 바꿈
            byteBuffer.flip();

            var result = StandardCharsets.UTF_8.decode(byteBuffer);
            log.info("result: {}", result);
        }

        log.info("end main");
    }
}
