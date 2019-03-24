package com.kims.gaming.server.service;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
@Service
public class PlayerConnectionListener {
    @Autowired
    private void openConnection() throws IOException {
        log.info("Starting....");
        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        DatagramSocket socket = new DatagramSocket(1234);
        boolean listen = true;
        while(listen) {
            byte buffer[] = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, 256);
            socket.receive(packet);
            String msg = new String(packet.getData());
            System.out.println("Cleint : " + msg);
            json.put("status", "connnected");
            byte buf[] = json.toJSONString().getBytes();
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            socket.send(packet);
        }
    }
}
