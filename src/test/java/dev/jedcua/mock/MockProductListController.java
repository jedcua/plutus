package dev.jedcua.mock;

import dev.jedcua.controller.ProductListController;
import dev.jedcua.model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public final class MockProductListController extends ProductListController {
    private static final Logger LOGGER = LogManager.getLogger(ProductListController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOGGER.info("Mocking initialize()");
    }

    @Override
    public void loadStore(Store store) {
        LOGGER.info("Mocking loadStore()");
    }

    @Override
    public void newProduct() {
        LOGGER.info("Mocking newProduct()");
    }

    @Override
    public void backToStoreList() {
        LOGGER.info("Mocking backToStoreList()");
    }
}
