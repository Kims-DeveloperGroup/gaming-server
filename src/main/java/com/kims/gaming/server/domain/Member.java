package com.kims.gaming.server.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

/* DTO(Data Transfer Object)
    */
@Data
public class Member {
    @Id
    private String id;
    private String userName;
    private int port;
    private String ip;
}
