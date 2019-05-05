package com.kims.gaming.server.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import reactor.netty.Connection;

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
        PlayerConnectionListener listener = new PlayerConnectionListener(LOCAL_HOST, SERVER_PORT);
        Connection serverConnection = listener.openConnection();
      latch.await();
    }

}
