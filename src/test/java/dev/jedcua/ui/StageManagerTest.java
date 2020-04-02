package dev.jedcua.ui;

import dev.jedcua.DependencyManager;
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
public final class StageManagerTest {
    private Stage stage;

    @BeforeEach
    public void beforeEach() {
        DependencyManager
            .initialize()
            .register(new StageManager(stage, 100, 100));
    }

    @AfterEach
    public void afterEach() {
        DependencyManager.destroy();
    }

    @Start
    private void start(final Stage stage) {
        this.stage = stage;
    }

    @Test
    public void loadModule(FxRobot robot) {
        final StageManager stageManager = new StageManager(stage, 100, 100);
        Assertions.assertDoesNotThrow(() -> {
            robot.interact(() -> {
                stageManager.loadModule(Module.WELCOME);
            });
        });
    }
}