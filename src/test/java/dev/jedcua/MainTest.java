package dev.jedcua;

import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class MainTest {
    private Stage stage;

    @Start
    private void start(final Stage stage) {
        this.stage = stage;
    }

    @Test
    public void start(final FxRobot robot) {
        Assertions.assertDoesNotThrow(() -> {
            robot.interact(() -> {
                new Main().start(this.stage);
            });
        });
    }
}