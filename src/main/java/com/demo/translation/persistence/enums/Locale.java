package com.demo.translation.persistence.enums;

public enum Locale {
    EN("en"),
    FR("fr"),
    ES("es"),
    DE("de"),
    IT("it");

    private final String code;

    Locale(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
