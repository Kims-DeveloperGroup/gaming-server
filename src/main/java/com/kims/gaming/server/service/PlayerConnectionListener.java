package com.kims.gaming.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
@Service
public class PlayerConnectionListener {
    @Bean
    private void openConnection() throws IOException {
        log.info("Starting....");
        byte buffer[] = new byte[1024];
        DatagramSocket socket = new DatagramSocket(1234);
        DatagramPacket packet = new DatagramPacket(buffer, 1024);
        boolean listen = true;
        while(listen) {
            socket.receive(packet);
            String msg = new String(packet.getData());
            System.out.println("Cleint : " + msg);
            String text = "connected";
            buffer = text.getBytes();
            packet = new DatagramPacket(buffer, 1024);
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        }
    }
}
