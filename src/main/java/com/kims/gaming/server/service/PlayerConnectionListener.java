package com.kims.gaming.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        udpServer = UdpServer.create().host(host).port(port);
        udpServer.doOnBound(con -> log.info("rikim Bound"));
        log.info("Connection Listener has been initialized. {}:{}", host, port);
    }

    public Connection openConnection() {
        Connection connection = udpServer.bindNow(Duration.ofMinutes(1l));
        log.info("Listener is bound");
        return connection;
    }
}
