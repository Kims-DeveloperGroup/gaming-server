package com.kims.gaming.server;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserRepository extends MongoRepository<Person, String>, QueryByExampleExecutor<Person> {
}
