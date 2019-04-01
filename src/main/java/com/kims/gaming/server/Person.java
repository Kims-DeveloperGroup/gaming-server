package com.kims.gaming.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@RequiredArgsConstructor
@Document(collection = "name")
public class Person extends MemberInfo {
    private final String userName, name;
}
