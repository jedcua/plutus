package dev.jedcua.ui;

import dev.jedcua.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public final class StageManager {
    private static final Logger LOGGER = LogManager.getLogger(StageManager.class);
    private Stage stage;

    public StageManager(final Stage stage, final Integer width, final Integer height) {
        this.stage = stage;
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public void loadModule(final Module module) {
        this.loadModule(module, () -> this.stage);
    }

    public void loadModule(final Module module, final Supplier<Stage> stageSupplier) {
        try {
            final FXMLLoader loader = new FXMLLoader(
                Main.class
                    .getClassLoader()
                    .getResource(module.getFilename())
            );
            final Parent root = loader.load();
            final Scene scene = new Scene(root);
            final Stage stg = stageSupplier.get();
            stg.setScene(scene);
            stg.show();
        } catch (final Exception exp) {
            LOGGER.error(exp.getMessage(), exp);
            throw new IllegalStateException(exp);
        }
    }
}
