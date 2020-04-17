package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
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
public class ProductListControllerTest {
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

        final StageManager stageManager = DependencyManager
            .getInstance()
            .fetch(StageManager.class);

        DependencyManager
            .getInstance()
            .register(new MockProductRepositoryImpl(product));

        final ProductListController[] controller = new ProductListController[1];

        robot.interact(() -> stageManager.loadModule(
            Module.PRODUCT_LIST,
            (loader) -> controller[0] = loader.getController()
        ));

        Assertions.assertDoesNotThrow(() -> {
            robot.interact(() -> controller[0].loadStore(
                new Store(1L, "Name", "Address", "Tin")
            ));
        });

        MatcherAssert.assertThat(
            controller[0].products,
            Matchers.hasSize(1)
        );

        MatcherAssert.assertThat(
            controller[0].products,
            Matchers.hasSize(1)
        );

        MatcherAssert.assertThat(
            controller[0].products,
            Matchers.contains(product)
        );
    }

    @Test
    public void newProduct(final FxRobot robot) {
        final StageManager stageManager = DependencyManager
            .getInstance()
            .fetch(StageManager.class);

        final ProductListController[] controller = new ProductListController[1];

        robot.interact(() -> stageManager.loadModule(
            Module.PRODUCT_LIST,
            (loader) -> controller[0] = loader.getController()
        ));

        robot.interact(() -> Assertions.assertDoesNotThrow(
            controller[0]::newProduct
        ));
    }

    @Test
    public void updateProduct(final FxRobot robot) {
        final StageManager stageManager = DependencyManager
            .getInstance()
            .fetch(StageManager.class);
        final Store store = DependencyManager
            .getInstance()
            .fetch(StoreRepository.class)
            .list()
            .get(0);

        final ProductListController[] controller = new ProductListController[1];

        robot.interact(() -> stageManager.loadModule(
            Module.PRODUCT_LIST,
            (loader) -> {
                controller[0] = loader.getController();
                controller[0].loadStore(store);
            }
        ));

        robot.interact(() -> {
            controller[0].tblProducts.getSelectionModel().selectLast();
            Assertions.assertDoesNotThrow(controller[0]::updateProduct);
        });
    }

    @Test
    public void deleteProduct(final FxRobot robot) {
        final StageManager stageManager = DependencyManager
            .getInstance()
            .fetch(StageManager.class);
        final Store store = DependencyManager
            .getInstance()
            .fetch(StoreRepository.class)
            .list()
            .get(0);

        final ProductListController[] controller = new ProductListController[1];

        robot.interact(() -> stageManager.loadModule(
            Module.PRODUCT_LIST,
            (loader) -> {
                controller[0] = loader.getController();
                controller[0].loadStore(store);
            }
        ));

        robot.interact(() -> {
            controller[0].tblProducts.getSelectionModel().selectLast();
            Assertions.assertDoesNotThrow(controller[0]::deleteProduct);
        });
    }

    @Test
    public void backToStoreList(final FxRobot robot) {
        final StageManager stageManager = DependencyManager
            .getInstance()
            .fetch(StageManager.class);

        final ProductListController[] controller = new ProductListController[1];

        robot.interact(() -> stageManager.loadModule(
            Module.PRODUCT_LIST,
            (loader) -> controller[0] = loader.getController()
        ));

        robot.interact(() -> Assertions.assertDoesNotThrow(
            controller[0]::backToStoreList
        ));
    }
}