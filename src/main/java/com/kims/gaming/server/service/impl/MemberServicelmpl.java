package com.kims.gaming.server.service.impl;

import com.kims.gaming.server.MemberRepo;
import com.kims.gaming.server.domain.Member;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class MemberServicelmpl {
    @Autowired
    private MemberRepo memberRepo;
    private MongoTemplate mongoTemplate;

    public List<Member> insert(Member member) {
        int port = member.getPort();
        mongoTemplate.insert(member);
        return memberRepo.findByPort(port);
    }
    public List<Member> update(Member member) {

        ObjectId id = new ObjectId(member.getId());
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.set("userName ", member.getUserName());

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Member.class);
        log.info(updateResult.toString());
        int port = member.getPort();
        return memberRepo.findByPort(port);
    }
}
