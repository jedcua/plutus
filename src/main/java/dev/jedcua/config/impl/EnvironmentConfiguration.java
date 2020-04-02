package dev.jedcua.config.impl;

import dev.jedcua.config.Configuration;

public final class EnvironmentConfiguration implements Configuration {
    @Override
    public String dbUrl() {
        return System.getenv("PLUTUS_DB_URL");
    }

    @Override
    public String dbUsername() {
        return System.getenv("PLUTUS_DB_USERNAME");
    }

    @Override
    public String dbPassword() {
        return System.getenv("PLUTUS_DB_PASSWORD");
    }
}
