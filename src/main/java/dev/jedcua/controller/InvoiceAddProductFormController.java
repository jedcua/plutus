package dev.jedcua.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dev.jedcua.DependencyManager;
import dev.jedcua.db.ProductRepository;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;
import dev.jedcua.ui.product.ProductTableRow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class InvoiceAddProductFormController implements Initializable {
    @FXML
    public JFXTextField txtFldSearch;

    @FXML
    public JFXTextField txtFldQuantity;

    @FXML
    public JFXButton btnAdd;

    @FXML
    public JFXButton btnCancel;

    @FXML
    public TableView<ProductTableRow> tblProducts;

    public Store store;
    public Product product;
    private final ProductRepository productRepository;

    public InvoiceAddProductFormController(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public InvoiceAddProductFormController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(ProductRepository.class)
        );
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        final ChangeListener<String> editListener = (observable, oldValue, newValue) -> {
            this.validate();
        };
        this.txtFldQuantity.textProperty().addListener(editListener);
        this.txtFldSearch.textProperty().addListener(this::handleSearchProducts);
        this.tblProducts.getSelectionModel().selectedItemProperty().addListener(this::handleSelectProduct);
    }

    @FXML
    public void close(final ActionEvent event) {
        final Stage stage = (Stage) this.btnCancel.getScene().getWindow();
        stage.close();
    }

    public void loadData(final Store store) {
        this.store = store;
    }

    private void initializeTable(final List<Product> products) {
        this.tblProducts.setItems(
            FXCollections.observableArrayList(products
                .stream()
                .map(ProductTableRow::new)
                .collect(Collectors.toList())
            )
        );
    }

    private void validate() {
        final boolean allValid = Stream.of(
            this.txtFldQuantity.validate(),
            this.product != null
        ).allMatch(b -> b);

        this.btnAdd.setDisable(!allValid);
    }

    private void handleSearchProducts(
        final ObservableValue<? extends String> obsrvble,
        final String oldVal,
        final String newVal
    ) {
        final List<Product> products;
        if (newVal != null && newVal.length() >= 3) {
            products = this.productRepository.search(this.store, newVal);
        } else {
            products = Collections.emptyList();
        }
        this.initializeTable(products);
    }

    private void handleSelectProduct(
        final ObservableValue<? extends ProductTableRow> obsrvbl,
        final ProductTableRow oldVal,
        final ProductTableRow newVal
    ) {
        this.product = Optional
            .ofNullable(newVal)
            .map(Product::fromTableRow)
            .orElse(null);
        this.validate();
    }
}
