package dev.jedcua;

import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main extends Application {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void start(final Stage stage) {
        LOGGER.info("Starting Plutus");

        DependencyManager
            .initialize()
            .register(new StageManager(stage, WIDTH, HEIGHT));

        DependencyManager
            .getInstance()
            .fetch(StageManager.class)
            .loadModule(Module.STORE_LIST);
    }

    public static void main(final String... args) {
        launch();
    }
}
