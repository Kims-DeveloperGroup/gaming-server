package com.kims.gaming.server.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/* DTO(Data Transfer Object)
    */
@Data
@Document(collection="gaming")
public class MemberDomain {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String userName;
    private int port;
    private String ip;
}
