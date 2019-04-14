package com.kims.gaming.server;

import com.kims.gaming.server.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/* DAO(Data Access Object)
   Service와 DB 연결 */
public interface MemberRepo extends MongoRepository<Member, Long> {
    public List<Member> findByPort(int port);
    public List<Member> findAll();
}
