package dev.jedcua.ui;

import dev.jedcua.model.Module;
import javafx.stage.Stage;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public final class StageManagerTest {
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
    public void getInstanceUninitialized() {
        Assertions.assertThrows(
            IllegalStateException.class,
            StageManager::getInstance
        );
    }

    @Test
    public void getInstanceInitialized() {
        StageManager.initialize(stage, 100, 100);
        MatcherAssert.assertThat(
            StageManager.getInstance(),
            Matchers.notNullValue()
        );
    }

    @Test
    public void loadModule(FxRobot robot) {
        StageManager.initialize(stage, 100, 100);
        Assertions.assertDoesNotThrow(() -> {
            robot.interact(() -> {
                StageManager.getInstance().loadModule(Module.WELCOME);
            });
        });
    }
}