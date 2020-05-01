package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class InvoicePreviewControllerTest {
    private Stage stage;

    @Start
    private void start(final Stage stage) {
        this.stage = stage;
    }

    @BeforeEach
    public void beforeEach() {
        DependencyManager
            .initialize()
            .register(new StageManager(stage));
    }
    @Test
    public void close(final FxRobot robot) {
        final InvoicePreviewController[] controller = new InvoicePreviewController[1];
        robot.interact(() -> {
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
                .loadModule(
                    Module.INVOICE_PREVIEW,
                    loader -> controller[0] = loader.getController()
                );
        });
        robot.interact(() -> Assertions.assertDoesNotThrow(() -> {
            controller[0].close(null);
        }));
    }
}