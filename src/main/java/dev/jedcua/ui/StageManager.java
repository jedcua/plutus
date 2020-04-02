package dev.jedcua.ui;

import dev.jedcua.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public final class StageManager {
    private static final Logger LOGGER = LogManager.getLogger(StageManager.class);
    private Stage stage;

    public StageManager(final Stage stage, final Integer width, final Integer height) {
        this.stage = stage;
        stage.setWidth(width);
        stage.setHeight(height);
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
