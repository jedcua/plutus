package dev.jedcua.controller;

import dev.jedcua.model.Store;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public final class StoreItemController implements Initializable {
    @FXML
    public Label storeName;

    @FXML
    public Label storeAddress;

    @FXML
    public Label storeTin;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) { }

    public void loadData(final Store store) {
        this.storeName.setText(store.getName());
        this.storeAddress.setText(store.getAddress());
        this.storeTin.setText(store.getTin());
    }
}
