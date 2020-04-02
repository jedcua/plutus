package dev.jedcua.ui;

import dev.jedcua.Main;
import dev.jedcua.model.Module;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public final class StageManager {
    private static StageManager instance;
    private static final Logger LOGGER = LogManager.getLogger(StageManager.class);
    private Stage stage;

    private StageManager(final Stage stage) {
        this.stage = stage;
    }

    public static void initialize(final Stage stage, final Integer width, final Integer height) {
        stage.setWidth(width);
        stage.setHeight(height);
        instance = new StageManager(stage);
    }

    public static StageManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Singleton is null!");
        }
        return instance;
    }

    public static void destroy() {
        instance = null;
    }

    public void loadModule(final Module module) {
        final Parent root;
        try {
            root = FXMLLoader.load(
                Objects.requireNonNull(
                    Main.class
                        .getClassLoader()
                        .getResource(module.getFilename())
                )
            );
            final Scene scene = new Scene(root);

            this.stage.setScene(scene);
            this.stage.show();
        } catch (final Exception exp) {
            LOGGER.error(exp.getMessage(), exp);
            throw new IllegalStateException(exp);
        }
    }
}
