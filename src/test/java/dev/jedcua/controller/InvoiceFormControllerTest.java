package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.mock.MockInvoiceFormController;
import dev.jedcua.mock.MockProductRepositoryImpl;
import dev.jedcua.mock.MockStoreRepositoryImpl;
import dev.jedcua.model.Product;
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
public class InvoiceFormControllerTest {
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
            ))
            .register(new MockProductRepositoryImpl());
    }

    @AfterEach
    public void afterAll() {
        DependencyManager.destroy();
    }

    @Test
    public void backToWelcome(final FxRobot robot) {
        final InvoiceFormController[] controller = new InvoiceFormController[1];
        robot.interact(() -> {
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
                .loadModule(
                    Module.INVOICE_FORM,
                    loader -> controller[0] = loader.getController()
                );
        });
        robot.interact(() -> Assertions.assertDoesNotThrow(() ->
            controller[0].backToWelcome(null))
        );
    }

    @Test
    public void openAddProductForm(final FxRobot robot) {
        final InvoiceFormController[] controller = new InvoiceFormController[1];
        robot.interact(() -> {
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
                .loadModule(
                    Module.INVOICE_FORM,
                    loader -> controller[0] = loader.getController()
                );
        });
        robot.interact(() -> Assertions.assertDoesNotThrow(() ->
            controller[0].openAddProductForm(null))
        );
    }

    @Test
    public void addInvoiceProduct(final FxRobot robot) {
        final InvoiceFormController[] controller = new InvoiceFormController[1];
        robot.interact(() -> {
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
                .loadModule(
                    Module.INVOICE_FORM,
                    loader -> controller[0] = loader.getController()
                );
        });
        robot.interact(() -> controller[0].addInvoiceProduct(
            new Product(1L, "Name", null, 12.20, "pcs"),
            1
        ));
    }
}