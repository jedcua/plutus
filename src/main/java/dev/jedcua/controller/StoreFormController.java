package dev.jedcua.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dev.jedcua.DependencyManager;
import dev.jedcua.db.StoreRepository;
import dev.jedcua.model.Store;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public final class StoreFormController implements Initializable {
    @FXML
    public JFXTextField txtFldName;

    @FXML
    public JFXTextField txtFldAddress;

    @FXML
    public JFXTextField txtFldTin;

    @FXML
    public JFXButton btnSave;

    @FXML
    public JFXButton btnCancel;

    @FXML
    public Label lblTitle;

    public Long storeId;
    private final StoreListController listController;
    private final StoreRepository repository;

    public StoreFormController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(StoreListController.class),
            DependencyManager
                .getInstance()
                .fetch(StoreRepository.class)
        );
    }

    public StoreFormController(
        final StoreListController listController,
        final StoreRepository repository
    ) {
        this.listController = listController;
        this.repository = repository;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final ChangeListener<String> editListener = (observable, oldValue, newValue) -> {
            this.validate();
        };
        this.txtFldName.textProperty().addListener(editListener);
        this.txtFldAddress.textProperty().addListener(editListener);
        this.txtFldTin.textProperty().addListener(editListener);
    }

    private void validate() {
        final boolean allValid = Stream.of(
            this.txtFldName.validate(),
            this.txtFldAddress.validate(),
            this.txtFldTin.validate()
        ).allMatch(b -> b);

        this.btnSave.setDisable(!allValid);
    }

    @FXML
    public void save(final ActionEvent event) {
        this.repository.save(
            new Store(storeId, txtFldName.getText(), txtFldAddress.getText(), txtFldTin.getText())
        );
        this.listController.resetItems();
        this.close(event);
    }

    @FXML
    public void close(final ActionEvent event) {
        final Stage stage = (Stage) this.btnCancel.getScene().getWindow();
        stage.close();
    }

    public void loadData(final Store store) {
        this.storeId = store.getId();
        this.txtFldName.setText(store.getName());
        this.txtFldAddress.setText(store.getAddress());
        this.txtFldTin.setText(store.getTin());

        if (this.storeId != null) {
            this.lblTitle.setText("Update " + this.lblTitle.getText());
        }
    }
}
