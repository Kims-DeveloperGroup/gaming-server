package com.kims.gaming.server;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReplyRepo extends MongoRepository<ReplyDomain, Long> {
    public List<ReplyDomain> findByBno(int bno);
    public List<ReplyDomain> findAll();
}
