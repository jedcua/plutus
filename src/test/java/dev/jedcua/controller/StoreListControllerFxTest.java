package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.FxTestUtils;
import dev.jedcua.mock.MockStoreRepositoryImpl;
import dev.jedcua.model.Page;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.stage.Stage;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Arrays;

@ExtendWith(ApplicationExtension.class)
public class StoreListControllerFxTest {
    private Stage stage;
    private Store storeFirst;

    @Start
    private void start(final Stage stage) {
        this.stage = stage;
    }

    @BeforeEach
    public void beforeEach() {
        this.storeFirst = new Store(1L, "Name", "Address", "Tin");
        DependencyManager
            .initialize()
            .register(new StageManager(stage))
            .register(new MockStoreRepositoryImpl(
                storeFirst,
                new Store(2L, "Name", "Address", "Tin"),
                new Store(3L, "Name", "Address", "Tin"),
                new Store(4L, "Name", "Address", "Tin"),
                new Store(5L, "Name", "Address", "Tin"),
                new Store(6L, "Name", "Address", "Tin")
            ));
    }

    @AfterEach
    public void afterAll() {
        DependencyManager.destroy();
    }

    @Test
    public void loadNewStoresOnScrollEnd(final FxRobot robot) {
        robot.interact(() -> {
            final StoreListController controller = FxTestUtils.loadModuleReturnController(
                Module.STORE_LIST, StoreListController.class
            );
            controller.lastPage = new Page<>(Arrays.asList(this.storeFirst), 0);
            controller.loadNewStoresOnScrollEnd(null, 0.90, 1.00);
            MatcherAssert.assertThat(controller.lastPage.getItems(), Matchers.hasSize(5));
        });
    }

    @Test
    public void openStoreForm(final FxRobot robot) {
        robot.interact(() -> {
            final StoreListController controller = FxTestUtils.loadModuleReturnController(
                Module.STORE_LIST, StoreListController.class
            );
            Assertions.assertDoesNotThrow(() -> controller.openStoreForm(null));
        });
    }

    @Test
    public void backToWelcome(final FxRobot robot) {
        robot.interact(() -> {
            final StoreListController controller = FxTestUtils.loadModuleReturnController(
                Module.STORE_LIST, StoreListController.class
            );
            Assertions.assertDoesNotThrow(() -> controller.backToWelcome(null));
        });
    }

    @Test
    public void confirmDelete(final FxRobot robot) {
        robot.interact(() -> {
            final StoreListController controller = FxTestUtils.loadModuleReturnController(
                Module.STORE_LIST, StoreListController.class
            );
            final Store store = controller.lastPage.getItems().get(0);
            Assertions.assertDoesNotThrow(() -> controller.confirmDelete(store));
        });
    }
}