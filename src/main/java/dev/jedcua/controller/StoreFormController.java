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

    private final StoreRepository repository;

    public StoreFormController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(StoreRepository.class)
        );
    }

    public StoreFormController(final StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        DependencyManager.getInstance().register(this);

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
            new Store(null, txtFldName.getText(), txtFldAddress.getText(), txtFldTin.getText())
        );
        this.close(event);
    }

    @FXML
    public void close(final ActionEvent event) {
        final Stage stage = (Stage) this.btnCancel.getScene().getWindow();
        stage.close();
    }
}
