package dev.jedcua.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dev.jedcua.DependencyManager;
import dev.jedcua.db.ProductRepository;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public final class ProductFormController implements Initializable {
    @FXML
    public JFXTextField txtFldName;

    @FXML
    public JFXTextField txtFldBarcode;

    @FXML
    public JFXTextField txtFldPrice;

    @FXML
    public JFXTextField txtFldUnit;

    @FXML
    public JFXButton btnSave;

    @FXML
    public JFXButton btnCancel;

    @FXML
    public Label lblTitle;

    public Store store;
    public Long productId;
    private final ProductListController listController;
    private final ProductRepository repository;

    public ProductFormController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(ProductListController.class),
            DependencyManager
                .getInstance()
                .fetch(ProductRepository.class)
        );
    }

    public ProductFormController(final ProductListController listController, final ProductRepository repository) {
        this.listController = listController;
        this.repository = repository;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final ChangeListener<String> editListener = (observable, oldValue, newValue) -> {
            this.validate();
        };
        this.txtFldName.textProperty().addListener(editListener);
        this.txtFldBarcode.textProperty().addListener(editListener);
        this.txtFldPrice.textProperty().addListener(editListener);
        this.txtFldUnit.textProperty().addListener(editListener);
    }

    private void validate() {
        final boolean allValid = Stream.of(
            this.txtFldName.validate(),
            this.txtFldBarcode.validate(),
            this.txtFldPrice.validate(),
            this.txtFldUnit.validate()
        ).allMatch(b -> b);

        this.btnSave.setDisable(!allValid);
    }

    @FXML
    public void save(final ActionEvent event) {
        this.repository.save(
            this.store,
            new Product(
                this.productId,
                this.txtFldName.getText(),
                this.txtFldBarcode.getText(),
                Double.valueOf(this.txtFldPrice.getText()),
                this.txtFldUnit.getText()
            )
        );
        this.listController.loadStore(this.store);
        this.close(event);
    }

    @FXML
    public void close(final ActionEvent event) {
        final Stage stage = (Stage) this.btnCancel.getScene().getWindow();
        stage.close();
    }

    public void loadData(final Store store, final Product product) {
        this.store = store;
        Optional
            .ofNullable(product.getId())
            .ifPresent(id -> {
                this.productId = id;
                this.lblTitle.setText("Update " + this.lblTitle.getText());
            });
        Optional
            .ofNullable(product.getName())
            .ifPresent(name -> this.txtFldName.setText(name));
        Optional
            .ofNullable(product.getBarcode())
            .ifPresent(barcode -> this.txtFldBarcode.setText(barcode));
        Optional
            .ofNullable(product.getPrice())
            .map(String::valueOf)
            .ifPresent(price -> this.txtFldPrice.setText(price));
        Optional
            .ofNullable(product.getUnit())
            .ifPresent(unit -> this.txtFldUnit.setText(unit));
    }
}
