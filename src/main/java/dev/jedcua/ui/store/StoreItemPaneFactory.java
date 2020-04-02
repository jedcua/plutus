package dev.jedcua.ui.store;

import dev.jedcua.controller.StoreItemController;
import dev.jedcua.model.Store;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public final class StoreItemPaneFactory {
    private static final Logger LOGGER = LogManager.getLogger(StoreItemPaneFactory.class);
    private final URL url;

    public StoreItemPaneFactory(final URL url) {
        this.url = url;
    }

    public AnchorPane build(final Store store) {
        try {
            final FXMLLoader loader = new FXMLLoader(this.url);
            final AnchorPane pane = loader.load();
            ((StoreItemController) loader.getController()).loadData(store);

            return pane;
        } catch (final Exception exp) {
            LOGGER.error(exp.getMessage(), exp);
            throw new IllegalStateException(exp);
        }
    }
}
