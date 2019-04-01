package com.kims.gaming.server.service;

import com.kims.gaming.server.ReplyDomain;
import java.util.List;

public interface ReplyService {
    public List<ReplyDomain> findbyBno(int bno);
    public List<ReplyDomain> findAll();
    public List<ReplyDomain> findByCondition();

    public List<ReplyDomain> insert(ReplyDomain reply);
    public List<ReplyDomain> update(ReplyDomain reply);
    public List<ReplyDomain> delete(ReplyDomain reply);
}
