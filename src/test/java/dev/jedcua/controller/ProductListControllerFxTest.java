package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.FxTestUtils;
import dev.jedcua.db.StoreRepository;
import dev.jedcua.mock.MockProductRepositoryImpl;
import dev.jedcua.mock.MockStoreRepositoryImpl;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.stage.Stage;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class ProductListControllerFxTest {
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
                new Product(1L, "Name", "Barcode", 12.23, "Unit")
            ))
            .register(new MockStoreRepositoryImpl(
                new Store(1L, "Name", "Address", "Tin"),
                new Store(2L, "Name", "Address", "Tin"),
                new Store(3L, "Name", "Address", "Tin"),
                new Store(4L, "Name", "Address", "Tin"),
                new Store(5L, "Name", "Address", "Tin")
            ));
    }

    @AfterEach
    public void afterAll() {
        DependencyManager.destroy();
    }

    @Test
    public void loadStore(final FxRobot robot) {
        final Product product = new Product(
            1L,
            "Name",
            "Barcode",
            123.45,
            "Unit"
        );

        DependencyManager
            .getInstance()
            .register(new MockProductRepositoryImpl(product));

        robot.interact(() -> {
            final ProductListController controller = FxTestUtils.loadModuleReturnController(
                Module.PRODUCT_LIST, ProductListController.class
            );
            Assertions.assertDoesNotThrow(() -> controller.loadStore(
                new Store(1L, "Name", "Address", "Tin")
            ));

            MatcherAssert.assertThat(controller.products, Matchers.hasSize(1));
            MatcherAssert.assertThat(controller.products, Matchers.hasSize(1));
            MatcherAssert.assertThat(controller.products, Matchers.contains(product));
        });
    }

    @Test
    public void newProduct(final FxRobot robot) {
        robot.interact(() -> {
            final ProductListController controller = FxTestUtils.loadModuleReturnController(
                Module.PRODUCT_LIST, ProductListController.class
            );
            Assertions.assertDoesNotThrow(controller::newProduct);
        });
    }

    @Test
    public void updateProduct(final FxRobot robot) {
        final Store store = DependencyManager
            .getInstance()
            .fetch(StoreRepository.class)
            .list()
            .get(0);

        robot.interact(() -> {
            final ProductListController controller = FxTestUtils.loadModuleReturnController(
                Module.PRODUCT_LIST, ProductListController.class
            );
            controller.loadStore(store);
            controller.tblProducts.getSelectionModel().selectLast();
            Assertions.assertDoesNotThrow(controller::updateProduct);
        });
    }

    @Test
    public void deleteProduct(final FxRobot robot) {
        final Store store = DependencyManager
            .getInstance()
            .fetch(StoreRepository.class)
            .list()
            .get(0);

        robot.interact(() -> {
            final ProductListController controller = FxTestUtils.loadModuleReturnController(
                Module.PRODUCT_LIST, ProductListController.class
            );
            controller.loadStore(store);
            controller.tblProducts.getSelectionModel().selectLast();
            Assertions.assertDoesNotThrow(controller::deleteProduct);
        });
    }

    @Test
    public void backToStoreList(final FxRobot robot) {
        robot.interact(() -> {
            final ProductListController controller = FxTestUtils.loadModuleReturnController(
                Module.PRODUCT_LIST, ProductListController.class
            );
            Assertions.assertDoesNotThrow(controller::backToStoreList);
        });
    }
}