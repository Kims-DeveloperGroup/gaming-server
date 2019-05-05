package com.kims.gaming.server.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GameEvent {
    private EventType eventType;

    public enum EventType {
        LOGIN, LOGOUT
    }
}
