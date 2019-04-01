package com.kims.gaming.server;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

@Data
@Document(collection="gaming")
public class ReplyDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String userName;
    private int port;
    private String ip;
}
