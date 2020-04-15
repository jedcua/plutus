package dev.jedcua.controller;

import com.jfoenix.controls.JFXButton;
import dev.jedcua.DependencyManager;
import dev.jedcua.db.ProductRepository;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import dev.jedcua.ui.product.ProductTableRow;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("checkstyle:designforextension")
public class ProductListController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger(ProductListController.class);

    @FXML
    public StackPane stackPaneRoot;

    @FXML
    public Label lblProducts;

    @FXML
    public TableView<ProductTableRow> tblProducts;

    @FXML
    public JFXButton btnUpdate;

    @FXML
    public JFXButton btnDelete;

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
        this.tblProducts.getSelectionModel().selectedIndexProperty().addListener(
            (observable, oldValue, newValue) -> {
                this.btnUpdate.setDisable(newValue.intValue() < 0);
                this.btnDelete.setDisable(newValue.intValue() < 0);
            }
        );
    }

    public void loadStore(final Store store) {
        LOGGER.info("Fetch Products | Store: {}", store.getName());
        this.lblProducts.setText("Products for " + store.getName());
        this.store = store;
        this.products = this.repository.list(this.store);

        this.initializeTable(this.products);
    }

    @FXML
    public void newProduct() {
        this.stageManager.loadModule(
            Module.PRODUCT_FORM,
            Stage::new,
            this.loadStoreAndProduct(Product.empty())
        );
    }

    @FXML
    public void updateProduct() {
        final Product product = Product.fromTableRow(
            this.tblProducts
                .getSelectionModel()
                .getSelectedItem()
        );
        this.stageManager.loadModule(
            Module.PRODUCT_FORM,
            Stage::new,
            this.loadStoreAndProduct(product)
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

    private Consumer<FXMLLoader> loadStoreAndProduct(final Product product) {
        return loader -> {
            final ProductFormController formCtrl = loader.getController();
            formCtrl.loadData(this.store, product);
        };
    }
}
