package javanio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;

@Slf4j
public class ServerSocketChannelNonBlockingExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        // 1. serverChannel open 및 어드레스 바인딩
        try(var serverSocketChannel = ServerSocketChannel.open()) {
            var address = new InetSocketAddress("localhost", 8080);
            serverSocketChannel.bind(address);
            serverSocketChannel.configureBlocking(false); // non-blocking 모드로 동작
            // 2. 클라이언트에 accept && clientChannel 가져옴
            var socketChannel = serverSocketChannel.accept();
            assert socketChannel == null;

            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            socketChannel.read(buffer);
            buffer.flip();

            var request = new String(buffer.array()).trim();
            log.info("request: {}", request);

            // 4. 서버에서 응답을 해줌
            var response = "This is server.";
            var responseBuffer = ByteBuffer.wrap(response.getBytes());
            socketChannel.write(responseBuffer);
            responseBuffer.flip();
            log.info("end main");
        }
    }
}
