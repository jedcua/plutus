package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.FxTestUtils;
import dev.jedcua.mock.MockInvoiceFormController;
import dev.jedcua.mock.MockProductRepositoryImpl;
import dev.jedcua.mock.MockStoreRepositoryImpl;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import dev.jedcua.ui.product.ProductTableRow;
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
public class InvoiceAddProductFormControllerFxTest {
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
            .register(new MockProductRepositoryImpl(
                new Product(1L, "Name", "Barcode", 123.20, "pcs")
            ))
            .register(new MockStoreRepositoryImpl(
                new Store(1L, "Name", "Address", "Tin")
            ))
            .register(new MockInvoiceFormController());
    }

    @AfterEach
    public void afterAll() {
        DependencyManager.destroy();
    }

    @Test
    public void add(final FxRobot robot) {
        robot.interact(() -> {
            final InvoiceAddProductFormController controller = FxTestUtils.loadModuleReturnController(
                Module.INVOICE_ADD_PRODUCT_FORM, InvoiceAddProductFormController.class
            );
            controller.loadData(new Store(1L, "Name", "Address", "Tin"));
            controller.txtFldQuantity.setText("1");
            Assertions.assertDoesNotThrow(() -> controller.add(null));
        });
    }

    @Test
    public void close(final FxRobot robot) {
        robot.interact(() -> {
            final InvoiceAddProductFormController controller = FxTestUtils.loadModuleReturnController(
                Module.INVOICE_ADD_PRODUCT_FORM, InvoiceAddProductFormController.class
            );
            controller.loadData(new Store(1L, "Name", "Address", "Tin"));
            Assertions.assertDoesNotThrow(() -> controller.close(null));
        });
    }

    @Test
    public void handleSearchProductsInvalid(final FxRobot robot) {
        robot.interact(() -> {
            final InvoiceAddProductFormController controller = FxTestUtils.loadModuleReturnController(
                Module.INVOICE_ADD_PRODUCT_FORM, InvoiceAddProductFormController.class
            );
            controller.loadData(new Store(1L, "Name", "Address", "Tin"));
            Assertions.assertDoesNotThrow(
                () -> controller.handleSearchProducts(null, null, null)
            );
        });
    }

    @Test
    public void handleSearchProducts(final FxRobot robot) {
        robot.interact(() -> {
            final InvoiceAddProductFormController controller = FxTestUtils.loadModuleReturnController(
                Module.INVOICE_ADD_PRODUCT_FORM, InvoiceAddProductFormController.class
            );
            controller.loadData(new Store(1L, "Name", "Address", "Tin"));
            Assertions.assertDoesNotThrow(
                () -> controller.handleSearchProducts(null, null, "Nam")
            );
        });
    }

    @Test
    public void handleSelectProduct(final FxRobot robot) {
        robot.interact(() -> {
            final InvoiceAddProductFormController controller = FxTestUtils.loadModuleReturnController(
                Module.INVOICE_ADD_PRODUCT_FORM, InvoiceAddProductFormController.class
            );
            controller.loadData(new Store(1L, "Name", "Address", "Tin"));
            Assertions.assertDoesNotThrow(() ->
                controller.handleSelectProduct(null, null, new ProductTableRow(
                    new Product(1L, "Name", "Barcode", 123.20, "pcs")
                ))
            );
        });
    }
}