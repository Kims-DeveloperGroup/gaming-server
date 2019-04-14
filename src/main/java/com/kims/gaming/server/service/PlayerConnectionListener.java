package com.kims.gaming.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service //DAO로 DB에 접근하고 DTO로 데이터를 전달받은 다음, 비지니스 로직을 처리해 적절한 데이터를 반환
public class PlayerConnectionListener {
    @Autowired
    public MemberRepoService memberRepoService; //repository의 method 사용

    @Autowired
    private void openConnection() throws Exception {

        memberRepoService.getMemberInfo();
        memberRepoService.connection();

    }
}
