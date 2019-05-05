package com.kims.gaming.server.service;

import com.kims.gaming.server.domain.GameEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.Connection;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;
import reactor.netty.udp.UdpClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class PlayerConnectionListenerTest {
    private final String LOCAL_HOST = "127.0.0.1";
    private final int SERVER_PORT = 9000;

    @Before
    public void connectToServer() {

    }

    @After
    public void closeAllConnections() {
    }

    @Test
    public void whenBindingServerToAPort_thenAcceptingIncomingMessagesFromClient() throws InterruptedException {
        // Given
        CountDownLatch latch = new CountDownLatch(1);

        // When
        Connection serverConnection = new PlayerConnectionListener(LOCAL_HOST, SERVER_PORT).openConnection();

        latch.await();
    }

}
