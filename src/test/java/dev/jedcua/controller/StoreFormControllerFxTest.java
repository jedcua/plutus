package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.FxTestUtils;
import dev.jedcua.db.StoreRepository;
import dev.jedcua.mock.MockStoreListController;
import dev.jedcua.mock.MockStoreRepositoryImpl;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.stage.Stage;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

@ExtendWith(ApplicationExtension.class)
public class StoreFormControllerFxTest {
    private Stage stage;

    @Start
    private void start(final Stage stage) {
        this.stage = stage;
    }

    @BeforeEach
    public void beforeEach() {
        DependencyManager
            .initialize()
            .register(new StageManager(stage))
            .register(new MockStoreRepositoryImpl())
            .register(new MockStoreListController());
    }

    @AfterEach
    public void afterAll() {
        DependencyManager.destroy();
    }

    @Test
    public void save(final FxRobot robot) {
        robot.interact(() -> {
            final StoreFormController controller = FxTestUtils.loadModuleReturnController(
                Module.STORE_FORM, StoreFormController.class
            );
            controller.txtFldName.setText("StoreName");
            controller.txtFldAddress.setText("StoreAddress");
            controller.txtFldTin.setText("StoreTin");
            Assertions.assertDoesNotThrow(() -> controller.save(null));
        });

        final List<Store> stores = DependencyManager
            .getInstance()
            .fetch(StoreRepository.class)
            .list();

        MatcherAssert.assertThat(stores, hasSize(1));
        MatcherAssert.assertThat(
            stores.get(0),
            allOf(
                hasProperty("name", is("StoreName")),
                hasProperty("address", is("StoreAddress")),
                hasProperty("tin", is("StoreTin"))
            )
        );
    }
}