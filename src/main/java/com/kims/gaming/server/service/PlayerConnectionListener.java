package com.kims.gaming.server.service;

import com.kims.gaming.server.ReplyDomain;
import com.kims.gaming.server.ReplyRepo;
import com.kims.gaming.server.ReplyServicelmpl;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

@Slf4j
@Service
public class PlayerConnectionListener {
    @Autowired
    private ReplyRepo replyRepo;
    private ReplyServicelmpl replyServicelmpl;
    @Autowired
    private void openConnection() throws IOException {
        log.info("Starting....");
        List<ReplyDomain> all = replyRepo.findAll();
        log.info(String.valueOf(all));

        JSONObject json = new JSONObject();
        DatagramSocket socket = new DatagramSocket(1234);
        boolean listen = true;
        while (listen) {
            byte buffer[] = new byte[256];
            DatagramPacket receiverPacket = new DatagramPacket(buffer, 256);
            socket.receive(receiverPacket);
            String msg = new String(receiverPacket.getData());
            log.info("connected with Client..\nfrom. Client : " + msg);
            json.put("status", "connnected");
            byte buffer2[] = json.toJSONString().getBytes();
            InetAddress address = receiverPacket.getAddress();
            int port = receiverPacket.getPort();
            DatagramPacket senderPacket = new DatagramPacket(buffer2, buffer2.length, address, port);
            socket.send(senderPacket);
            if (msg == "disconnected") {
                log.info("server was disconnected");
                socket.close();
            }
        }
    }
}
