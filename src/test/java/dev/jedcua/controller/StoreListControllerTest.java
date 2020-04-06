package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
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
public class StoreListControllerTest {
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
            .register(new StageManager(stage, 100, 100))
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
        final StageManager stageManager = DependencyManager
            .getInstance()
            .fetch(StageManager.class);

        final StoreListController[] controller = new StoreListController[1];

        robot.interact(() -> stageManager.loadModule(
            Module.STORE_LIST,
            (loader) -> controller[0] = loader.getController()
        ));

        controller[0].lastPage = new Page<>(Arrays.asList(this.storeFirst), 0);

        Assertions.assertDoesNotThrow(() -> {
            robot.interact(() -> controller[0].loadNewStoresOnScrollEnd(
                null,
                0.90,
                1.00
            ));
        });

        MatcherAssert.assertThat(
            controller[0].lastPage.getItems(),
            Matchers.hasSize(5)
        );
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

    @Test
    public void confirmDelete(final FxRobot robot) {
        final StageManager stageManager = DependencyManager
            .getInstance()
            .fetch(StageManager.class);

        final StoreListController[] controller = new StoreListController[1];

        robot.interact(() -> stageManager.loadModule(
            Module.STORE_LIST,
            (loader) -> controller[0] = loader.getController()
        ));

        final Store store = controller[0].lastPage.getItems().get(0);

        Assertions.assertDoesNotThrow(() -> {
            robot.interact(() -> controller[0].confirmDelete(store));
        });
    }
}