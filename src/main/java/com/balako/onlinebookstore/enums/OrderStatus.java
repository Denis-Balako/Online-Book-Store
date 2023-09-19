package com.balako.onlinebookstore.enums;

public enum OrderStatus {
    CREATED,
    PENDING,
    CANCELED,
    DELIVERED,
    COMPLETED;

    public static OrderStatus getDefaultStatus() {
        return CREATED;
    }
}
