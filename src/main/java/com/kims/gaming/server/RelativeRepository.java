package com.kims.gaming.server;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface RelativeRepository extends MongoRepository<Relative, String>, QueryByExampleExecutor<Relative> {
}
