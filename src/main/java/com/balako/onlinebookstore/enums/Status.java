package com.balako.onlinebookstore.enums;

public enum Status {
    CREATED,
    PENDING,
    CANCELED,
    DELIVERED,
    COMPLETED;

    public static Status getDefaultStatus() {
        return CREATED;
    }
}
