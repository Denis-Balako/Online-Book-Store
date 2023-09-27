package com.balako.onlinebookstore.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class CustomPostgresContainer extends PostgreSQLContainer<CustomPostgresContainer> {
    private static final String IMAGE_VERSION = "postgres:16-alpine";
    private static final String URL = "DB_URL";
    private static final String USERNAME = "DB_USERNAME";
    private static final String PASSWORD = "DB_PASSWORD";
    private static CustomPostgresContainer container;

    private CustomPostgresContainer() {
        super(IMAGE_VERSION);
    }

    public static CustomPostgresContainer getInstance() {
        if (container == null) {
            container = new CustomPostgresContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty(URL, container.getJdbcUrl());
        System.setProperty(USERNAME, container.getUsername());
        System.setProperty(PASSWORD, container.getPassword());
    }

    @Override
    public void stop() {
    }
}
