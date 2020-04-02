package dev.jedcua.db;

import dev.jedcua.config.Configuration;
import org.jdbi.v3.core.Jdbi;

import java.sql.DriverManager;
import java.sql.SQLException;

public final class JdbiFactory {
    private JdbiFactory() { }

    public static Jdbi initialize(final Configuration configuration) throws SQLException {
        return Jdbi.create(
            DriverManager.getConnection(
                configuration.dbUrl(),
                configuration.dbUsername(),
                configuration.dbPassword()
            )
        );
    }
}
