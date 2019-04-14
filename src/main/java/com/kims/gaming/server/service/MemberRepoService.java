package com.kims.gaming.server.service;

import com.kims.gaming.server.MemberRepo;
import com.kims.gaming.server.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

@Slf4j
@Service
public class MemberRepoService {
    @Autowired
    private MemberRepo memberRepo;
    private DatagramSocket socket;
    private byte[] buffer = new byte[256];
    private DatagramPacket receiver = new DatagramPacket(buffer, 256);

    public void getMemberInfo() {
        List<Member> getMemberInfo = memberRepo.findAll();
        log.info(String.valueOf(getMemberInfo));
    }
    public void disconnecter() throws Exception {
        log.info("Please Wait...Server will be closed in 2 seconds");
        Thread.sleep(2000);
        socket.close();
    }
    public void connection() throws Exception{
        JSONObject json = new JSONObject();
        DatagramSocket socket = new DatagramSocket(1234);
        boolean run = true;
        while(run) {
            socket.receive(receiver);
            String msg = new String(receiver.getData(), 0, receiver.getLength());
            log.info("***Client : " + msg);
            if(msg.equals("quit")) { disconnecter(); }
            json.put("status", "connnected");
            byte buffer2[] = json.toJSONString().getBytes();
            InetAddress address = receiver.getAddress();
            int port = receiver.getPort();
            DatagramPacket senderPacket = new DatagramPacket(buffer2, buffer2.length, address, port);
            socket.send(senderPacket);
        }
    }
}
