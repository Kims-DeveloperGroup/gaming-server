package com.kims.gaming.server.service;

import com.kims.gaming.server.MemberInfo;
import com.kims.gaming.server.MemberRepository;
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
    /*@Autowired
    private MemberRepository memberRepository;

    public void memberRepository() {
        System.out.println(memberRepository.findAll());
    }*/
    @Autowired
    private void openConnection() throws IOException {
        MemberInfo memberInfo = new MemberInfo();
        log.info(memberInfo.toString()); //MemberInfo에서 받은 mongoDB파일 로깅
        log.info("Starting....");
        JSONObject json = new JSONObject();
        DatagramSocket socket = new DatagramSocket(1234);
        while (true) {
            byte buffer[] = new byte[256];
            DatagramPacket receiverPacket = new DatagramPacket(buffer, 256);
            socket.receive(receiverPacket);
            String msg = new String(receiverPacket.getData());
            log.info("connected with Client..\nfrom. Client : " + msg);
            InetAddress address = receiverPacket.getAddress();
            int port = receiverPacket.getPort();
            json.put("status", "connnected");
            byte buffer2[] = json.toJSONString().getBytes();
            DatagramPacket senderPacket = new DatagramPacket(buffer2, buffer2.length, address, port);
            socket.send(senderPacket);
            if (msg.equals("disconnected")) {
                log.info("server was disconnected");
                socket.close();
            }
        }
    }
}
