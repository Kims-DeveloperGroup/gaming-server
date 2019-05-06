package com.kims.gaming.server.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GameEvent {
    private EventType eventType;

    public enum EventType {
        LOGIN, LOGOUT, EXCEPTION
    }
}
