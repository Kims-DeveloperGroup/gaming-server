package com.kims.gaming.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kims.gaming.server.domain.GameEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.udp.UdpServer;

import java.io.IOException;

@Slf4j
@Service
public class PlayerConnectionListener {
    private final UdpServer udpServer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PlayerConnectionListener(String host,  int port) {
        udpServer = UdpServer.create().host(host).port(port)
                .doOnBound(con -> log.info("Connection listener is bound. connection: {}", con))
                .doOnUnbound(con -> log.info("Connection listener unbound, {}", con))
                .handle((in, out) ->
                        out.sendObject(
                                in.receiveObject()
                                        .map(o -> {
                                            if (o instanceof DatagramPacket) {
                                                DatagramPacket packet = (DatagramPacket) o;
                                                GameEvent event = deserializePacketToGameEvent(packet);
                                                log.info("Inbound packet {} from {}", event, packet.sender());
                                                return new DatagramPacket(packet.content(), packet.sender());
                                            } else {
                                                return Mono.error(new Exception("Unexpected type of the message: " + o));
                                            }
                                        })));
        log.info("Connection Listener has been initialized. {}:{}", host, port);
    }

    public Connection openConnection() {
        return udpServer.bind().block();
    }

    private GameEvent deserializePacketToGameEvent(DatagramPacket packet) {
        byte[] bytes = new byte[packet.content().readableBytes()];
        ByteBuf content = packet.content();
        while (content.isReadable()) {
            bytes[content.readerIndex()] = content.readByte();
        }
        GameEvent gameEvent;
        try {
            gameEvent = objectMapper.readValue(bytes, GameEvent.class);
        } catch (IOException e) {
            log.error("Fail to read data into game event", e);
            gameEvent = new GameEvent(GameEvent.EventType.EXCEPTION);
        }
        return gameEvent;
    }
}
