package com.kims.gaming.server.service;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Logger
@Service
public class ServerApplicationService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ServerApplicationService.class);
    @Autowired
    public void run() throws IOException {
        log.info("connecting");
        DatagramSocket socket = new DatagramSocket(1234);
        boolean listen = true;
        while (listen) {
            byte buffer[] = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, 1024);
            socket.receive(packet);
            String msg = new String(packet.getData());
            System.out.println("Cleint : " + msg);
            String text = "connected";
            buffer = text.getBytes();
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        }
    }
}
