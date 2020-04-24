package dev.jedcua.ui.store;

import dev.jedcua.DependencyManager;
import dev.jedcua.Main;
import dev.jedcua.mock.MockStoreListController;
import dev.jedcua.mock.MockStoreRepositoryImpl;
import dev.jedcua.model.Store;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.net.URL;
import java.util.stream.Collectors;

@ExtendWith(ApplicationExtension.class)
public class StoreItemPaneFactoryTest {
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
    public void build() {
        final URL url = Main.class.getClassLoader().getResource(Module.STORE_ITEM.getFilename());
        final Store store = new Store(1L, "StoreName", "StoreAddress", "StoreTin");
        MatcherAssert.assertThat(
            new StoreItemPaneFactory(url)
                .build(store)
                .getChildren()
                .stream()
                .filter(node -> node instanceof Label)
                .map(Label.class::cast)
                .map(Labeled::getText)
                .collect(Collectors.toList()),
            Matchers.containsInAnyOrder(
                store.getName(),
                store.getAddress(),
                store.getTin()
            )
        );
    }

    @Test
    public void buildUncheckedIOException() {
        final URL url = Main.class.getClassLoader().getResource("not-exist.xml");
        final Store store = new Store(1L, "StoreName", "StoreAddress", "StoreTin");
        Assertions.assertThrows(IllegalStateException.class, () -> {
            new StoreItemPaneFactory(url).build(store);
        });
    }
}