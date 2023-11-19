package javanio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SocketChanneNonBlockinglExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        // 1. socketChannel open 및 서버에 connect
        try(var socketChannel = SocketChannel.open()) {
            var address = new InetSocketAddress("localhost", 8080);
            socketChannel.configureBlocking(false); // non-blocking 모드 설정
            var connected = socketChannel.connect(address);
            assert !connected;
            log.info("connected: {}", connected);

            // 2. socketChannel 에 request write
            String request = "This is client.";
            ByteBuffer requestBuffer = ByteBuffer.wrap(request.getBytes());
            socketChannel.write(requestBuffer);
            requestBuffer.clear(); // 사용하지 않을 것이므로 초기화

            // 3. 요청에 대한 서버의 응답 가져오기
            ByteBuffer res = ByteBuffer.allocateDirect(1024);
            while (socketChannel.read(res) > 0){ // 서버가 값을 계속 주는 동안 read
                res.flip(); // filp 을 통해 읽어들임
                log.info("response: {}", StandardCharsets.UTF_8.decode(res));
                res.clear(); //  초기화해서 재사용하기
            }
        }
        log.info("end main");
    }
}
