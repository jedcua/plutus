package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.mock.MockStoreRepository;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class StoreItemControllerTest {
    private Stage stage;

    @Start
    private void start(final Stage stage) {
        this.stage = stage;
    }

    @BeforeEach
    public void beforeEach() {
        DependencyManager
            .initialize()
            .register(new StageManager(stage, 100, 100))
            .register(new MockStoreRepository());
    }

    @AfterEach
    public void afterAll() {
        DependencyManager.destroy();
    }

    @Test
    public void save(final FxRobot robot) {
        final StageManager stageManager = DependencyManager
            .getInstance()
            .fetch(StageManager.class);

        Assertions.assertDoesNotThrow(() -> {
            robot.interact(() ->
                stageManager.loadModule(Module.STORE_ITEM, this::invokeUpdate)
            );
        });
    }

    public void invokeUpdate(final FXMLLoader loader) {
        final StoreItemController controller = loader.getController();
        controller.loadData(
            new Store(1L, "Name", "Address", null)
        );
        controller.update(null);
    }
}