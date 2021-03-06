package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.FxTestUtils;
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
public class WelcomeControllerFxTest {
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
            .register(new MockStoreRepositoryImpl(
                new Store(1L, "Name", "Address", "Tin"),
                new Store(2L, "Name", "Address", "Tin"),
                new Store(3L, "Name", "Address", "Tin"),
                new Store(4L, "Name", "Address", "Tin"),
                new Store(5L, "Name", "Address", "Tin")
            ));
    }

    @AfterEach
    public void afterAll() {
        DependencyManager.destroy();
    }

    @Test
    public void openStores(final FxRobot robot) {
        robot.interact(() -> {
            final WelcomeController controller = FxTestUtils.loadModuleReturnController(
                Module.WELCOME, WelcomeController.class
            );
            Assertions.assertDoesNotThrow(() -> controller.openStores(null));
        });
    }

    @Test
    public void openInvoiceForm(final FxRobot robot) {
        robot.interact(() -> {
            final WelcomeController controller = FxTestUtils.loadModuleReturnController(
                Module.WELCOME, WelcomeController.class
            );
            Assertions.assertDoesNotThrow(() -> controller.openInvoiceForm(null));
        });
    }
}