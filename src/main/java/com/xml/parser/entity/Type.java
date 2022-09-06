package com.xml.parser.entity;

public enum Type {

    ELECTRICITY("electricity"),
    GASOLINE("gasoline"),
    NUCLEAR("nuclear");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    public String value() {
        return value.toLowerCase();
    }

    public static Type fromValue(String value) {
        for (Type type : Type.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
