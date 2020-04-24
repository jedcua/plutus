package dev.jedcua.controller;

import com.jfoenix.controls.JFXTextField;
import dev.jedcua.DependencyManager;
import dev.jedcua.Main;
import dev.jedcua.db.StoreRepository;
import dev.jedcua.model.Page;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import dev.jedcua.ui.store.StoreItemDeleteDialogFactory;
import dev.jedcua.ui.store.StoreItemPaneFactory;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("checkstyle:designforextension")
public class StoreListController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger(StoreListController.class);
    private static final int ITEMS_PER_PAGE = 5;
    private static final double SCROLL_VAL_END = 1.00;

    @FXML
    public VBox vBoxStores;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public JFXTextField txtFldSearch;

    @FXML
    public StackPane stackPaneRoot;

    public Page<Store> lastPage;
    private StoreItemPaneFactory factory;
    private final StoreRepository repository;
    private final StageManager stageManager;

    public StoreListController(final StoreRepository repository, final StageManager stageManager) {
        this.repository = repository;
        this.stageManager = stageManager;
    }

    public StoreListController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(StoreRepository.class),
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
        );
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        DependencyManager.getInstance().register(this);
        this.factory = new StoreItemPaneFactory(
            Main.class
                .getClassLoader()
                .getResource(Module.STORE_ITEM.getFilename())
        );
        this.scrollPane.vvalueProperty().addListener(this::loadNewStoresOnScrollEnd);
        this.txtFldSearch.textProperty().addListener((obsvbl, ov, nv) -> this.resetItems());
        this.resetItems();
    }

    public void resetItems() {
        this.lastPage = new Page.Empty<>();
        this.vBoxStores.getChildren().clear();
        this.scrollPane.setVvalue(0);
        this.pushToScrollPane(
            this.fetchStorePage().getItems()
        );
    }

    public void confirmDelete(final Store store) {
        StoreItemDeleteDialogFactory
            .build(store, dialog -> {
                this.repository.delete(store);
                this.resetItems();
                dialog.close();
            })
            .show(this.stackPaneRoot);
    }

    @FXML
    public void openStoreForm(final ActionEvent event) {
        this.stageManager.loadModule(
            Module.STORE_FORM,
            (Supplier<Stage>) Stage::new
        );
    }

    public void loadNewStoresOnScrollEnd(
        final ObservableValue<? extends Number> observable,
        final Number oldValue,
        final Number newValue
    ) {
        if (oldValue.doubleValue() < SCROLL_VAL_END
            && newValue.doubleValue() == SCROLL_VAL_END
            && this.lastPage.getItems().size() > 0
        ) {
            this.pushToScrollPane(
                this.fetchStorePage().getItems()
            );
        }
    }

    private Page<Store> fetchStorePage() {
        final String search = this.txtFldSearch.getText();
        final int nextOffset = this.lastPage.nextOffset();
        LOGGER.info("Fetch {} items | Offset: {} | Search: {}", ITEMS_PER_PAGE, nextOffset, search);

        this.lastPage = this.repository.page(this.lastPage.nextOffset(), ITEMS_PER_PAGE, search);
        return this.lastPage;
    }

    private void pushToScrollPane(final List<Store> stores) {
        this.vBoxStores.getChildren().addAll(
            stores
                .stream()
                .map(this.factory::build)
                .collect(Collectors.toList())
        );
    }
}
