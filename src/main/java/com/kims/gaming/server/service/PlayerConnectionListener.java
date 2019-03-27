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
        while (listen) {
            byte buffer[] = new byte[256];
            DatagramPacket RPacket = new DatagramPacket(buffer, 256);
            socket.receive(RPacket);
            String msg = new String(RPacket.getData());
            log.info("connected with Client..\nfrom. Client : " + msg);
            json.put("status", "connnected");
            byte buffer2[] = json.toJSONString().getBytes();
            InetAddress address = RPacket.getAddress();
            int port = RPacket.getPort();
            DatagramPacket SPacket = new DatagramPacket(buffer2, buffer2.length, address, port);
            socket.send(SPacket);
            if (msg == "disconnected") {
                log.info("server was disconnected");
                socket.close();
            }
        }
    }
}
