package com.kims.gaming.server;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.username}")
    private String userName;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Override
    protected String getDatabaseName() {
        return database;
    }
    /*   ------This method is deprecated(ServerAddress) --------
    public Mongo mongo() throws Exception {
        MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
        return new MongoClient(new ServerAddress("localhost", 27107), Arrays.asList(credential));
    }*/

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("localhost", 27017);
    }

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), database);
    }
}
