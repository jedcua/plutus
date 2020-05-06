package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.FxTestUtils;
import dev.jedcua.mock.MockInvoiceTemplateServiceImpl;
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
public class InvoicePreviewControllerFxTest {
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
            .register(new MockInvoiceTemplateServiceImpl());
    }
    @Test
    public void close(final FxRobot robot) {
        robot.interact(() -> {
            final InvoicePreviewController controller = FxTestUtils.loadModuleReturnController(
                Module.INVOICE_PREVIEW, InvoicePreviewController.class
            );
            Assertions.assertDoesNotThrow(() -> controller.close(null));
        });
    }
}