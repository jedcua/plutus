package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.db.ProductRepository;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import dev.jedcua.ui.product.ProductTableRow;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@SuppressWarnings("checkstyle:designforextension")
public class ProductListController implements Initializable {
    @FXML
    public StackPane stackPaneRoot;

    @FXML
    public Label lblProducts;

    @FXML
    public TableView<ProductTableRow> tblProducts;

    public Store store;
    public List<Product> products;
    private final StageManager stageManager;
    private final ProductRepository repository;

    public ProductListController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(StageManager.class),
            DependencyManager
                .getInstance()
                .fetch(ProductRepository.class)
        );
    }

    public ProductListController(final StageManager stageManager, final ProductRepository repository) {
        this.stageManager = stageManager;
        this.repository = repository;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        DependencyManager.getInstance().register(this);
    }

    public void loadStore(final Store store) {
        this.store = store;
        this.products = this.repository.list(this.store);

        this.lblProducts.setText(
            this.lblProducts.getText() + " for " + store.getName()
        );

        this.initializeTable(this.products);
    }

    @FXML
    public void newProduct() {
        this.stageManager.loadModule(
            Module.PRODUCT_FORM,
            Stage::new,
            loader -> {
                final ProductFormController formCtrl = loader.getController();
                formCtrl.loadData(this.store, Product.empty());
            }
        );
    }

    @FXML
    public void backToStoreList() {
        this.stageManager
            .loadModule(Module.STORE_LIST);
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
}
