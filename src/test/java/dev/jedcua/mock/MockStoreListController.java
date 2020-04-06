package dev.jedcua.mock;

import dev.jedcua.controller.StoreListController;
import dev.jedcua.model.Store;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public final class MockStoreListController extends StoreListController {
    private static final Logger LOGGER = LogManager.getLogger(StoreListController.class);

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        LOGGER.info("Mocking initialize()");
    }

    @Override
    public void resetItems() {
        LOGGER.info("Mocking resetItems()");
    }

    @Override
    public void confirmDelete(Store store) {
        LOGGER.info("Mocking confirmDelete()");
    }

    @Override
    public void openStoreForm(final ActionEvent event) {
        LOGGER.info("Mocking openStoreForm()");
    }

    @Override
    public void loadNewStoresOnScrollEnd(
        final ObservableValue<? extends Number> observable,
        final Number oldValue,
        final Number newValue
    ) {
        LOGGER.info("Mocking loadNewStoresOnScrollEnd()");
    }
}
