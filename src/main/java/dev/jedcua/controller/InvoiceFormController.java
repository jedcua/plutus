package dev.jedcua.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dev.jedcua.DependencyManager;
import dev.jedcua.db.StoreRepository;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import dev.jedcua.ui.invoice.InvoiceProductTableRow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@SuppressWarnings("checkstyle:designforextension")
public class InvoiceFormController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger(InvoiceFormController.class);

    @FXML
    public JFXComboBox<Label> cmbStores;

    @FXML
    public TableView<InvoiceProductTableRow> tblInvoiceProducts;

    @FXML
    public JFXButton btnAddProduct;

    public List<Store> stores;
    public Store selectedStore;

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
        DependencyManager.getInstance().register(this);
        this.stores = this.storeRepository.list();
        this.cmbStores.getItems().addAll(
            this.stores
                .stream()
                .map(s -> new Label(s.getName()))
                .collect(Collectors.toList())
        );
        this.cmbStores.getSelectionModel().selectedIndexProperty().addListener(
            (obsrvbl, oldIdx, newIdx) -> {
                this.selectedStore = this.stores.get(newIdx.intValue());
                this.btnAddProduct.setDisable(false);
                LOGGER.info("Store select | Index: {} | Name: {}", newIdx, this.selectedStore.getName());
            }
        );
    }

    @FXML
    public void backToWelcome(final ActionEvent event) {
        this.stageManager.loadModule(Module.WELCOME);
    }

    @FXML
    public void openAddProductForm(final ActionEvent event) {
        this.stageManager.loadModule(
            Module.INVOICE_ADD_PRODUCT_FORM,
            Stage::new,
            loader -> {
                final InvoiceAddProductFormController controller = loader.getController();
                controller.loadData(this.selectedStore);
            }
        );
    }

    public void addInvoiceProduct(final Product product, final int quantity) {
        this.tblInvoiceProducts.getItems().add(
            new InvoiceProductTableRow(product, quantity)
        );
    }
}
