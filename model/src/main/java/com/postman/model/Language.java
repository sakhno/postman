package com.postman.model;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public enum Language {
    EN, RU;

    @Override
    public String toString() {
        switch (this) {
            case EN:
                return "English";
            case RU:
                return "Русский";
            default:
                return this.name();
        }
    }
}
