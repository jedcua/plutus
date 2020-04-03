package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.mock.MockStoreRepository;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
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
public class StoreListControllerTest {
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
    public void openStoreForm(final FxRobot robot) {
        final StageManager stageManager = DependencyManager
            .getInstance()
            .fetch(StageManager.class);

        final StoreListController[] controller = new StoreListController[1];

        robot.interact(() -> stageManager.loadModule(
            Module.STORE_LIST,
            (loader) -> controller[0] = loader.getController()
        ));

        Assertions.assertDoesNotThrow(() -> {
            robot.interact(() -> controller[0].openStoreForm(null));
        });
    }
}