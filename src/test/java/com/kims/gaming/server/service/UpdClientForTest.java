package com.kims.gaming.server.service;

import com.kims.gaming.server.domain.GameEvent;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import reactor.core.publisher.Mono;
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

    private UdpClient udpClient;

    private Connection clientConnection;

    public UpdClientForTest() {
        udpClient = UdpClient.create()
                .host(LOCAL_HOST)
                .port(SERVER_PORT)
                .doOnConnect(bootstrap -> log.info("On connect"))
                .doOnConnected(connection -> log.info("Client connected"))
                .handle((inbound, outbound) -> outbound.sendString(Mono.just("hello")));
    }

    public void connect() {
        clientConnection = udpClient.connect().block();
    }

    public void send(GameEvent gameEvent) {
        clientConnection.outbound().sendString(Mono.just("HI"));
//        clientConnection.outbound().sendObject(gameEvent);
        log.info("Message was sent");
    }

    public void send(String msg) {
        clientConnection.outbound().sendString(Mono.just(msg));
    }

    public void closeConnection() {
        clientConnection.disposeNow();

    }

    public static void main(String[] args) throws IOException {
//        UpdClientForTest client = new UpdClientForTest();
//        client.connect();
//        int count = 0;
//        while(true) {
//            client.send(new GameEvent(GameEvent.EventType.LOGIN));
//            count++;
//        }

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
