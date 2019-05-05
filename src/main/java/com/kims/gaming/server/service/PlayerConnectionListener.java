package com.kims.gaming.server.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.udp.UdpServer;

@Slf4j
@Service //DAO로 DB에 접근하고 DTO로 데이터를 전달받은 다음, 비지니스 로직을 처리해 적절한 데이터를 반환
public class PlayerConnectionListener {
    @Autowired
    public MemberRepoService memberRepoService; //repository의 method 사용
    private final UdpServer udpServer;

    public PlayerConnectionListener(String host,  int port) {
        udpServer = UdpServer.create().host(host).port(port)
                .doOnUnbound(con -> {
                    log.info("On unbound, {}", con);
                })
                .doOnBound(con -> log.info("rikim Bound"))
                .handle((in, out) ->
                        out.sendObject(
                                in.receiveObject()
                                        .map(o -> {
                                            log.info("received");
                                            if (o instanceof DatagramPacket) {
                                                DatagramPacket p = (DatagramPacket) o;
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
        Connection connection = udpServer.bind().block()
                .addHandler(new LineBasedFrameDecoder(1024))
                .addHandler(new ChannelHandlerAdapter() {
                    @Override
                    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                        super.handlerAdded(ctx);
                        log.info("");
                    }

                    @Override
                    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                        super.handlerRemoved(ctx);
                        log.info("Handler removed");
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        super.exceptionCaught(ctx, cause);
                    }
                });
        log.info("Listener is bound");
        return connection;
    }
}
