package com.kims.gaming.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Document(collection = "name")
public class MemberInfo {
    private @Getter
    ObjectId id; String userName; String name;
    @Override
    public String toString() {
        return String.format("<id> :" + id + " <userName> : " + userName + " <name> : " + name);
    }
}
