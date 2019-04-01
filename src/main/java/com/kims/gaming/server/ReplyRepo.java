package com.kims.gaming.server;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReplyRepo extends MongoRepository<ReplyDomain, Long> {
    public List<ReplyDomain> findByPort(int port);
    public List<ReplyDomain> findAll();
}
