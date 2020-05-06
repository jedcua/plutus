package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.FxTestUtils;
import dev.jedcua.db.ProductRepository;
import dev.jedcua.mock.MockProductListController;
import dev.jedcua.mock.MockProductRepositoryImpl;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.stage.Stage;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

@ExtendWith(ApplicationExtension.class)
public class ProductFormControllerFxTest {
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
            .register(new MockProductRepositoryImpl())
            .register(new MockProductListController());

    }

    @AfterEach
    public void afterAll() {
        DependencyManager.destroy();
    }

    @Test
    public void save(final FxRobot robot) {
        final Store store = new Store(1L, null, null, null);

        robot.interact(() -> {
            final ProductFormController controller = FxTestUtils.loadModuleReturnController(
                Module.PRODUCT_FORM, ProductFormController.class
            );
            controller.loadData(store, Product.empty());
            controller.txtFldName.setText("ProductName");
            controller.txtFldBarcode.setText("ProductBarcode");
            controller.txtFldPrice.setText("12.34");
            controller.txtFldUnit.setText("ProductUnit");
            Assertions.assertDoesNotThrow(() -> controller.save(null));
        });

        final List<Product> stores = DependencyManager
            .getInstance()
            .fetch(ProductRepository.class)
            .list(store);

        MatcherAssert.assertThat(stores, hasSize(1));
        MatcherAssert.assertThat(
            stores.get(0),
            allOf(
                hasProperty("name", is("ProductName")),
                hasProperty("barcode", is("ProductBarcode")),
                hasProperty("price", is(12.34)),
                hasProperty("unit", is("ProductUnit"))
            )
        );
    }
}