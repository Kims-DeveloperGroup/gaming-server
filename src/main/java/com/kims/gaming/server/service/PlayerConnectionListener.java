package com.kims.gaming.server.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.udp.UdpServer;

@Slf4j
@Service
public class PlayerConnectionListener {
    private final UdpServer udpServer;

    public PlayerConnectionListener(String host,  int port) {
        udpServer = UdpServer.create().host(host).port(port)
                .doOnBound(con -> log.info("Connection listener is bound. connection: {}", con))
                .doOnUnbound(con -> log.info("Connection listener unbound, {}", con))
                .handle((in, out) ->
                        out.sendObject(
                                in.receiveObject()
                                        .map(o -> {
                                            if (o instanceof DatagramPacket) {
                                                DatagramPacket p = (DatagramPacket) o;
                                                log.info("inbound packet from {}", p.sender());                                                log.info("sender: {}", p.sender());
                                                ByteBuf buf = Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8);
                                                return new DatagramPacket(buf, p.sender());
                                            } else {
                                                return Mono.error(
                                                        new Exception("Unexpected type of the message: " + o));
                                            }
                                        })));
        log.info("Connection Listener has been initialized. {}:{}", host, port);
    }

    public Connection openConnection() {
        return udpServer.bind().block();
    }
}
