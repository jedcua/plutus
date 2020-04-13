package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
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
public class ProductFormControllerTest {
    private Stage stage;

    @Start
    private void start(final Stage stage) {
        this.stage = stage;
    }

    @BeforeEach
    public void beforeEach() {
        DependencyManager
            .initialize()
            .register(new StageManager(stage, 100, 100))
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
        final ProductFormController[] controller = new ProductFormController[1];

        robot.interact(() -> {
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
                .loadModule(
                    Module.PRODUCT_FORM,
                    (loader) -> {
                        controller[0] = loader.getController();
                        controller[0].loadData(store, Product.empty());
                    }
                );
        });

        robot.interact(() -> {
            controller[0].txtFldName.setText("ProductName");
            controller[0].txtFldBarcode.setText("ProductBarcode");
            controller[0].txtFldPrice.setText("12.34");
            controller[0].txtFldUnit.setText("ProductUnit");
            controller[0].save(null);
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