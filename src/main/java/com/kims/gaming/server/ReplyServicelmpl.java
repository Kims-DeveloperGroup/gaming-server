package com.kims.gaming.server;

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
public class ReplyServicelmpl {
    @Autowired
    private ReplyRepo replyRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ReplyDomain> insert(ReplyDomain reply) {
        int bno = reply.getBno();
        mongoTemplate.insert(reply);
        return replyRepo.findByBno(bno);
    }

    public List<ReplyDomain> update(ReplyDomain reply) {
        int bno = reply.getBno();
        ObjectId id = new ObjectId(reply.getId());
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("userName", reply.getUserName());
        update.set("contents", reply.getContents());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, ReplyDomain.class);
        log.info(updateResult.toString());
        return replyRepo.findByBno(bno);
    }
}
