package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.Main;
import dev.jedcua.db.StoreRepository;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.store.StoreItemPaneFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public final class StoreListController implements Initializable {
    @FXML
    public VBox vBoxStores;

    private final StoreRepository repository;

    public StoreListController(final StoreRepository repository) {
        this.repository = repository;
    }

    public StoreListController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(StoreRepository.class)
        );
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final StoreItemPaneFactory factory = new StoreItemPaneFactory(
            Main.class
                .getClassLoader()
                .getResource(Module.STORE_ITEM.getFilename())
        );
        this.vBoxStores.getChildren().addAll(
            this.repository
                .list()
                .stream()
                .map(factory::build)
                .collect(Collectors.toList())
        );
    }
}
