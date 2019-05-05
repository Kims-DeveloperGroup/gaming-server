package com.kims.gaming.server.service;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.SimpleUserEventChannelHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.udp.UdpServer;

import java.time.Duration;

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
                .handle((in, out) -> // 연결된 커넥션에 대한 IN/OUT 처리
                        // reactor.netty (NettyInbound, NettyOutbound)
                        in.receive() // 데이터 읽기 선언, ByteBufFlux 리턴
                                .asString()  // 문자열로 변환 선언, Flux<String> 리턴
                                .flatMap(msg -> {
                                            log.debug("doOnNext: [{}]", msg);
                                            if (msg.equals("exit")) {
                                                return out.withConnection(conn -> conn.dispose());
                                            } else if (msg.equals("SHUTDOWN")) {
                                                return out;
                                            } else {
                                                return out.sendString(Mono.just("echo: " + msg + "\r\n"));
                                            }
                                        }
                                )
                );
        log.info("Connection Listener has been initialized. {}:{}", host, port);
    }

    public Connection openConnection() {
        Connection connection = udpServer.bind().block()
                .addHandler(new LineBasedFrameDecoder(1024))
                .addHandler(new ChannelInboundHandler() {
                    @Override
                    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
                        log.info("Registered.");
                    }

                    @Override
                    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
                        log.info("Unregistered");
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
                        log.info("channelActive");
                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
                        log.info("inactive channel");
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                        log.info("read: {}", o);
                    }

                    @Override
                    public void channelReadComplete(ChannelHandlerContext ctxt) throws Exception {
                        log.info("read complete. open: {}, active: {}",
                                ctxt.channel().isOpen(),
                                ctxt.channel().isActive());

                    }

                    @Override
                    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

                    }

                    @Override
                    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {

                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                        log.info("exception : {}", throwable);
                    }

                    @Override
                    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

                    }

                    @Override
                    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

                    }
                }).addHandler(new ChannelHandlerAdapter() {
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
