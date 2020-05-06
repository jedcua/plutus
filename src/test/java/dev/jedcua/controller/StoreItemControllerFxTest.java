package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.FxTestUtils;
import dev.jedcua.mock.MockProductRepositoryImpl;
import dev.jedcua.mock.MockStoreListController;
import dev.jedcua.mock.MockStoreRepositoryImpl;
import dev.jedcua.model.Store;
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
public class StoreItemControllerFxTest {
    private Stage stage;

    @Start
    private void start(final Stage stage) {
        this.stage = stage;
    }

    @BeforeEach
    public void beforeEach() {
        DependencyManager
            .initialize()
            .register(new StageManager(stage))
            .register(new MockStoreRepositoryImpl())
            .register(new MockStoreListController())
            .register(new MockProductRepositoryImpl());
    }

    @AfterEach
    public void afterAll() {
        DependencyManager.destroy();
    }

    @Test
    public void save(final FxRobot robot) {
        robot.interact(() -> {
            final StoreItemController controller = FxTestUtils.loadModuleReturnController(
                Module.STORE_ITEM, StoreItemController.class
            );
            controller.loadData(new Store(1L, "Name", "Address", null));
            Assertions.assertDoesNotThrow(() -> controller.update(null));
        });
    }

    @Test
    public void confirmDelete(final FxRobot robot) {
        robot.interact(() -> {
            final StoreItemController controller = FxTestUtils.loadModuleReturnController(
                Module.STORE_ITEM, StoreItemController.class
            );
            controller.loadData(new Store(1L, "Name", "Address", null));
            Assertions.assertDoesNotThrow(() -> controller.confirmDelete(null));
        });
    }

    @Test
    public void showProducts(final FxRobot robot) {
        robot.interact(() -> {
            final StoreItemController controller = FxTestUtils.loadModuleReturnController(
                Module.STORE_ITEM, StoreItemController.class
            );
            controller.loadData(new Store(1L, "Name", "Address", null));
            Assertions.assertDoesNotThrow(() -> controller.showProducts(null));
        });
    }
}