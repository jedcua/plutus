package dev.jedcua;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public final class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void start(final Stage stage) {
        // Setup logger
        BasicConfigurator.configure();
        LOGGER.info("Starting Plutus");

        StageManager.initialize(stage, WIDTH, HEIGHT);
        StageManager.getInstance().loadModule(Module.WELCOME);
    }

    public static void main(final String... args) {
        launch();
    }
}
