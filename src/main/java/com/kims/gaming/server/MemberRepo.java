package com.kims.gaming.server;

import com.kims.gaming.server.domain.MemberDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/* DAO(Data Access Object)
   Service와 DB 연결 */
public interface MemberRepo extends MongoRepository<MemberDomain, Long> {
    public List<MemberDomain> findByPort(int port);
    public List<MemberDomain> findAll();
}
