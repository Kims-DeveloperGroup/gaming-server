package com.kims.gaming.server.service;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
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
        DatagramSocket socket = new DatagramSocket(1234);
        boolean listen = true;
        while(listen) {
            byte buffer[] = new byte[256];
            DatagramPacket receivePacket = new DatagramPacket(buffer, 256);
            socket.receive(receivePacket);
            String msg = new String(receivePacket.getData());
            log.info(msg);
            json.put("status", "connnected");
            byte buf[] = json.toJSONString().getBytes();
            InetAddress address = receivePacket.getAddress();
            int port = receivePacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, address, port);
            socket.send(sendPacket);
            if (receivePacket == null) {
                break;
            }
        }

    }
}

