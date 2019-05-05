package com.kims.gaming.server.service;

import com.kims.gaming.server.domain.GameEvent;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import reactor.netty.Connection;
import reactor.netty.udp.UdpClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Duration;
import java.util.Scanner;

@Slf4j
public class UpdClientForTest {
    private final String LOCAL_HOST = "127.0.0.1";
    private final int SERVER_PORT = 9000;

    private UdpClient udpClient = UdpClient.create()
            .host(LOCAL_HOST)
            .port(SERVER_PORT);

    private Connection clientConnection;

    public void connect() {
        udpClient.doOnConnected(connection -> log.info("Client connected"));
        clientConnection = udpClient.connectNow(Duration.ofSeconds(3L));
    }

    public void send(GameEvent gameEvent) {
        clientConnection.outbound().sendObject(gameEvent);
    }

    public static void main(String[] args) throws IOException {
//        UpdClientForTest client = new UpdClientForTest();
//        client.connect();
//        client.send(new GameEvent(GameEvent.EventType.LOGIN));

        JSONObject obj = new JSONObject();
        obj.put("userName", "zucca");
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket sendPacket;
        DatagramPacket response;
        String sendMsg = obj.toJSONString();
        byte buf[] = sendMsg.getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        sendPacket = new DatagramPacket(buf, buf.length, address, 9000);
        socket.send(sendPacket);
        byte buffer[] = new byte[256];
        response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);
        String Rstr = new String(response.getData(), 0, response.getLength());
        System.out.println("Server : " + Rstr);

        while (true) {
            String message;
            Scanner scanner = new Scanner(System.in);
            message = scanner.nextLine();

            byte buffer2[] = message.getBytes();
            DatagramPacket sendPacket2 = new DatagramPacket(buffer2, buffer2.length, address, 9000);
            socket.send(sendPacket2);

        }
    }
}
