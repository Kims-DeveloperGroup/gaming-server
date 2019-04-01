package com.kims.gaming.server;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@EnableMongoRepositories
public interface MemberRepository extends MongoRepository<MemberInfo, Long>, QueryByExampleExecutor<MemberInfo> {}
