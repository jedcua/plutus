package dev.jedcua.controller;

import com.jfoenix.controls.JFXComboBox;
import dev.jedcua.DependencyManager;
import dev.jedcua.db.StoreRepository;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public final class InvoiceFormController implements Initializable {
    @FXML
    public JFXComboBox<Label> cmbStores;

    public List<Store> stores;

    private final StageManager stageManager;
    private final StoreRepository storeRepository;

    public InvoiceFormController(final StageManager stageManager, final StoreRepository storeRepository) {
        this.stageManager = stageManager;
        this.storeRepository = storeRepository;
    }

    public InvoiceFormController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(StageManager.class),
            DependencyManager
                .getInstance()
                .fetch(StoreRepository.class)
        );
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        this.stores = this.storeRepository.list();
        this.cmbStores.getItems().addAll(
            this.stores
                .stream()
                .map(s -> new Label(s.getName()))
                .collect(Collectors.toList())
        );
    }

    @FXML
    public void backToWelcome(final ActionEvent event) {
        this.stageManager.loadModule(Module.WELCOME);
    }
}
