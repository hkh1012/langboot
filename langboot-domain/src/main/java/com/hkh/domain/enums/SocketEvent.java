package com.hkh.domain.enums;

public enum SocketEvent {

    AUDIO("audio"),
    TEXT("text");

    private final String eventName;

    SocketEvent(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return this.eventName;
    }
}
