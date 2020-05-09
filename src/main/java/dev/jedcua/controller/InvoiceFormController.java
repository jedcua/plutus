package dev.jedcua.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import dev.jedcua.DependencyManager;
import dev.jedcua.db.StoreRepository;
import dev.jedcua.model.Invoice;
import dev.jedcua.model.Product;
import dev.jedcua.model.ProductWithQuantity;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import dev.jedcua.ui.invoice.InvoiceProductTableRow;
import dev.jedcua.utils.FormatUtils;
import javafx.collections.ListChangeListener;
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
import java.util.stream.Stream;

@SuppressWarnings("checkstyle:designforextension")
public class InvoiceFormController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger(InvoiceFormController.class);

    @FXML
    public JFXComboBox<Label> cmbStores;

    @FXML
    public TableView<InvoiceProductTableRow> tblInvoiceProducts;

    @FXML
    public JFXButton btnAddProduct;

    @FXML
    public JFXButton btnRemoveProduct;

    @FXML
    public JFXButton btnSave;

    @FXML
    public JFXButton btnPreview;

    @FXML
    public JFXDatePicker dpDeliveryDate;

    @FXML
    public Label lblTotal;

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
                this.validate();
                LOGGER.info("Store select | Index: {} | Name: {}", newIdx, this.selectedStore.getName());
            }
        );
        this.tblInvoiceProducts.getSelectionModel().selectedIndexProperty().addListener(
            (observable, oldValue, newValue) -> {
                this.btnRemoveProduct.setDisable(newValue.intValue() < 0);
            }
        );
        this.tblInvoiceProducts.getItems().addListener((ListChangeListener<InvoiceProductTableRow>) change -> {
            this.updateTotal();
        });
        this.dpDeliveryDate.valueProperty().addListener((obsrvbl, oldDate, newDate) -> {
            this.validate();
        });
    }

    @FXML
    public void backToWelcome(final ActionEvent event) {
        this.stageManager.loadModule(Module.WELCOME);
    }

    @FXML
    public void openPreview(final ActionEvent event) {
        this.stageManager.loadModule(
            Module.INVOICE_PREVIEW,
            Stage::new,
            loader -> {
                final InvoicePreviewController controller = loader.getController();
                controller.loadPreview(
                    new Invoice(
                        this.selectedStore,
                        this.tblInvoiceProducts
                            .getItems()
                            .stream()
                            .map(ProductWithQuantity::fromTableRow)
                            .collect(Collectors.toList()),
                        this.dpDeliveryDate.getValue()
                    )
                );
            }
        );
    }

    @FXML
    public void removeInvoiceProduct() {
        this.tblInvoiceProducts.getItems().remove(
            this.tblInvoiceProducts.getSelectionModel().getSelectedItem()
        );
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
        this.validate();
    }

    public void validate() {
        final boolean valid = Stream.of(
            this.cmbStores.validate(),
            this.dpDeliveryDate.validate(),
            !this.tblInvoiceProducts.getItems().isEmpty()
        ).allMatch(b -> b);

        this.btnSave.setDisable(!valid);
        this.btnPreview.setDisable(!valid);
    }

    public void updateTotal() {
        final double sum = this.tblInvoiceProducts
            .getItems()
            .stream()
            .map(InvoiceProductTableRow::getSubtotal)
            .reduce(Double::sum)
            .orElse(0.00);
        this.lblTotal.setText(FormatUtils.formatAmount(sum));
    }
}
