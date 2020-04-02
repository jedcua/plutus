package dev.jedcua.config;

public interface Configuration {
    String dbUrl();

    default String dbUsername() {
        return null;
    }

    default String dbPassword() {
        return null;
    }
}
