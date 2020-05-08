package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.FxTestUtils;
import dev.jedcua.mock.MockInvoiceTemplateServiceImpl;
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
public class InvoiceFormControllerFxTest {
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
            .register(new MockInvoiceTemplateServiceImpl())
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
        robot.interact(() -> {
            final InvoiceFormController controller = FxTestUtils.loadModuleReturnController(
                Module.INVOICE_FORM, InvoiceFormController.class
            );
            Assertions.assertDoesNotThrow(() -> controller.backToWelcome(null));
        });
    }

    @Test
    public void openAddProductForm(final FxRobot robot) {
        robot.interact(() -> {
            final InvoiceFormController controller = FxTestUtils.loadModuleReturnController(
                Module.INVOICE_FORM, InvoiceFormController.class
            );
            Assertions.assertDoesNotThrow(() -> controller.openAddProductForm(null));
        });
    }

    @Test
    public void addInvoiceProduct(final FxRobot robot) {
        robot.interact(() -> {
            final InvoiceFormController controller = FxTestUtils.loadModuleReturnController(
                Module.INVOICE_FORM, InvoiceFormController.class
            );
            final Product product = new Product(1L, "Name", "Barcode", 12.20, "pcs");
            Assertions.assertDoesNotThrow(
                () -> controller.addInvoiceProduct( product, 1)
            );
        });
    }

    @Test
    public void removeInvoiceProduct(final FxRobot robot) {
        robot.interact(() -> {
            final InvoiceFormController controller = FxTestUtils.loadModuleReturnController(
                Module.INVOICE_FORM, InvoiceFormController.class
            );
            final Product product = new Product(1L, "Name", null, 12.20, "pcs");
            controller.addInvoiceProduct( product, 1);
            controller.tblInvoiceProducts.getSelectionModel().selectLast();
            Assertions.assertDoesNotThrow(controller::removeInvoiceProduct);
        });
    }

    @Test
    public void openPreview(final FxRobot robot) {
        robot.interact(() -> {
            final InvoiceFormController controller = FxTestUtils.loadModuleReturnController(
                Module.INVOICE_FORM, InvoiceFormController.class
            );
            final Product product = new Product(1L, "Name", "Barcode", 12.20, "pcs");
            controller.cmbStores.getSelectionModel().select(0);
            controller.addInvoiceProduct( product, 1);
            Assertions.assertDoesNotThrow(
                () -> controller.openPreview(null)
            );
        });
    }
}