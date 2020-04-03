package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public final class StoreItemController implements Initializable {
    @FXML
    public Label storeName;

    @FXML
    public Label storeAddress;

    @FXML
    public Label storeTin;

    public Long storeId;
    private final StageManager stageManager;

    public StoreItemController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
        );
    }

    public StoreItemController(final StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) { }

    @FXML
    public void update(final ActionEvent event) {
        this.stageManager.loadModule(
            Module.STORE_FORM,
            Stage::new,
            this::transferData
        );
    }

    public void loadData(final Store store) {
        this.storeId = store.getId();
        this.storeName.setText(store.getName());
        this.storeAddress.setText(store.getAddress());
        this.storeTin.setText(store.getTin());
    }

    private void transferData(final FXMLLoader loader) {
        final StoreFormController formCtrl = loader.getController();
        formCtrl.loadData(
            new Store(
                this.storeId,
                this.storeName.getText(),
                this.storeAddress.getText(),
                this.storeTin.getText()
            )
        );
    }
}
