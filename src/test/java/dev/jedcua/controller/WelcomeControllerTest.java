package dev.jedcua.controller;

import dev.jedcua.ui.StageManager;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class WelcomeControllerTest {
    private Stage stage;

    @Start
    private void start(final Stage stage) {
        this.stage = stage;
    }

    @AfterEach
    public void afterAll() {
        StageManager.destroy();
    }

    @Test
    public void openStores(final FxRobot robot) {
        StageManager.initialize(stage, 100, 100);
        Assertions.assertDoesNotThrow(() -> {
            robot.interact(() -> {
                new WelcomeController().openStores(null);
            });
        });
    }
}