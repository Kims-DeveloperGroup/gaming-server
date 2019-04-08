package com.kims.gaming.server.service;

import com.kims.gaming.server.domain.MemberDomain;
import java.util.List;

public interface MemberService {
    public List<MemberDomain> findbyPort(int port);
    public List<MemberDomain> findAll();
    public List<MemberDomain> findByCondition();

    public List<MemberDomain> insert(MemberDomain reply);
    public List<MemberDomain> update(MemberDomain reply);
    public List<MemberDomain> delete(MemberDomain reply);
}
