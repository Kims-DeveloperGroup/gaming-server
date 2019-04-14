package com.kims.gaming.server.service;

import com.kims.gaming.server.domain.Member;
import java.util.List;

public interface MemberService {
    public List<Member> findbyPort(int port);
    public List<Member> findAll();
    public List<Member> findByCondition();

    public List<Member> insert(Member reply);
    public List<Member> update(Member reply);
    public List<Member> delete(Member reply);
}
