package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.mock.MockInvoiceFormController;
import dev.jedcua.mock.MockProductRepositoryImpl;
import dev.jedcua.mock.MockStoreRepositoryImpl;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import dev.jedcua.ui.product.ProductTableRow;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class InvoiceAddProductFormControllerTest {
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

    @Test
    public void add(final FxRobot robot) {
        final InvoiceAddProductFormController[] controller = new InvoiceAddProductFormController[1];
        robot.interact(() -> {
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
                .loadModule(
                    Module.INVOICE_ADD_PRODUCT_FORM,
                    loader -> {
                        controller[0] = loader.getController();
                        controller[0].loadData(
                            new Store(1L, "Name", "Address", "Tin")
                        );
                    }
                );
        });
        robot.interact(() -> controller[0].txtFldQuantity.setText("1"));
        robot.interact(() -> Assertions.assertDoesNotThrow(() ->
            controller[0].add(null))
        );
    }

    @Test
    public void close(final FxRobot robot) {
        final InvoiceAddProductFormController[] controller = new InvoiceAddProductFormController[1];
        robot.interact(() -> {
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
                .loadModule(
                    Module.INVOICE_ADD_PRODUCT_FORM,
                    loader -> {
                        controller[0] = loader.getController();
                        controller[0].loadData(
                            new Store(1L, "Name", "Address", "Tin")
                        );
                    }
                );
        });
        robot.interact(() -> Assertions.assertDoesNotThrow(() ->
            controller[0].close(null))
        );
    }

    @Test
    public void handleSearchProductsInvalid(final FxRobot robot) {
        final InvoiceAddProductFormController[] controller = new InvoiceAddProductFormController[1];
        robot.interact(() -> {
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
                .loadModule(
                    Module.INVOICE_ADD_PRODUCT_FORM,
                    loader -> {
                        controller[0] = loader.getController();
                        controller[0].loadData(
                            new Store(1L, "Name", "Address", "Tin")
                        );
                    }
                );
        });
        robot.interact(() -> Assertions.assertDoesNotThrow(() ->
            controller[0].handleSearchProducts(null, null, null)
        ));
    }

    @Test
    public void handleSearchProducts(final FxRobot robot) {
        final InvoiceAddProductFormController[] controller = new InvoiceAddProductFormController[1];
        robot.interact(() -> {
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
                .loadModule(
                    Module.INVOICE_ADD_PRODUCT_FORM,
                    loader -> {
                        controller[0] = loader.getController();
                        controller[0].loadData(
                            new Store(1L, "Name", "Address", "Tin")
                        );
                    }
                );
        });
        robot.interact(() -> Assertions.assertDoesNotThrow(() ->
            controller[0].handleSearchProducts(null, null, "Nam")
        ));
    }

    @Test
    public void handleSelectProduct(final FxRobot robot) {
        final InvoiceAddProductFormController[] controller = new InvoiceAddProductFormController[1];
        robot.interact(() -> {
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
                .loadModule(
                    Module.INVOICE_ADD_PRODUCT_FORM,
                    loader -> {
                        controller[0] = loader.getController();
                        controller[0].loadData(
                            new Store(1L, "Name", "Address", "Tin")
                        );
                    }
                );
        });
        robot.interact(() -> Assertions.assertDoesNotThrow(() ->
            controller[0].handleSelectProduct(null, null, new ProductTableRow(
                new Product(1L, "Name", "Barcode", 123.20, "pcs")
            ))
        ));
    }
}