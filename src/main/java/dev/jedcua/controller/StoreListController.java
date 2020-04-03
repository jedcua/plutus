package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.Main;
import dev.jedcua.db.StoreRepository;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import dev.jedcua.ui.store.StoreItemPaneFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public final class StoreListController implements Initializable {
    private static final int HEIGHT = 450;
    private static final int WIDTH = 600;

    @FXML
    public VBox vBoxStores;

    private final StoreRepository repository;
    private final StageManager stageManager;

    public StoreListController(final StoreRepository repository, final StageManager stageManager) {
        this.repository = repository;
        this.stageManager = stageManager;
    }

    public StoreListController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(StoreRepository.class),
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
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

    @FXML
    public void openStoreForm(final ActionEvent event) {
        this.stageManager.loadModule(
            Module.STORE_FORM,
            () -> {
                final Stage stage = new Stage();
                stage.setHeight(HEIGHT);
                stage.setWidth(WIDTH);
                return stage;
            }
        );
    }
}
