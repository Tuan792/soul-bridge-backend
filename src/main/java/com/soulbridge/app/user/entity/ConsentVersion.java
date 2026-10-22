package com.soulbridge.app.user.entity;

public enum ConsentVersion {
    V1_0("v1.0"),
    V2_0("v2.0");

    private final String value;

    ConsentVersion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}