package dev.jedcua;

import dev.jedcua.config.Configuration;
import dev.jedcua.config.impl.EnvironmentConfiguration;
import dev.jedcua.db.JdbiFactory;
import dev.jedcua.db.impl.ProductRepositoryImpl;
import dev.jedcua.db.impl.StoreRepositoryImpl;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.Jdbi;

import java.sql.SQLException;

public final class Main extends Application {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private final Configuration configuration;

    public Main() {
        this(new EnvironmentConfiguration());
    }

    public Main(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void start(final Stage stage) {
        try {
            LOGGER.info("Starting Plutus");
            final Jdbi jdbi = JdbiFactory.initialize(this.configuration);

            DependencyManager
                .initialize()
                .register(new StageManager(stage))
                .register(new StoreRepositoryImpl(jdbi))
                .register(new ProductRepositoryImpl(jdbi));

            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
                .loadModule(Module.STORE_LIST);
        } catch (final SQLException exp) {
            LOGGER.error(exp.getMessage(), exp);
            throw new RuntimeException(exp);
        }
    }

    public static void main(final String... args) {
        launch();
    }
}
