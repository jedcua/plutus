package dev.jedcua.ui;

import dev.jedcua.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class StageManager {
    private static final Logger LOGGER = LogManager.getLogger(StageManager.class);
    private Stage stage;

    public StageManager(final Stage stage) {
        this.stage = stage;
    }

    public void loadModule(final Module module) {
        this.loadModule(module, () -> this.stage, loader -> { });
    }

    public void loadModule(final Module module, final Supplier<Stage> stageSupplier) {
        this.loadModule(module, stageSupplier, loader -> { });
    }

    public void loadModule(final Module module, final Consumer<FXMLLoader> callback) {
        this.loadModule(module, () -> this.stage, callback);
    }

    public void loadModule(
        final Module module,
        final Supplier<Stage> stageSupplier,
        final Consumer<FXMLLoader> callback
    ) {
        try {
            final FXMLLoader loader = new FXMLLoader(
                Main.class
                    .getClassLoader()
                    .getResource(module.getFilename())
            );
            final Parent root = loader.load();
            callback.accept(loader);
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
