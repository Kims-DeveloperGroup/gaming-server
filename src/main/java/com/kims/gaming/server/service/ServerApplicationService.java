package com.kims.gaming.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


@Service

public class ServerApplicationService {

    @Autowired
    public void run() throws IOException {
        System.out.println("연결 대기중");
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
