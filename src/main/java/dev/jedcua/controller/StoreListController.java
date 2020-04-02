package dev.jedcua.controller;

import dev.jedcua.Main;
import dev.jedcua.ui.Module;
import dev.jedcua.model.Store;
import dev.jedcua.ui.store.StoreItemPaneFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public final class StoreListController implements Initializable {
    @FXML
    public VBox vBoxStores;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final StoreItemPaneFactory factory = new StoreItemPaneFactory(
            Main.class
                .getClassLoader()
                .getResource(Module.STORE_ITEM.getFilename())
        );
        this.vBoxStores.getChildren().addAll(
            factory.build(new Store(1L, "Store Name", "Store Address", "TIN-123456789")),
            factory.build(new Store(1L, "Store Name", "Store Address", null))
        );
    }
}
