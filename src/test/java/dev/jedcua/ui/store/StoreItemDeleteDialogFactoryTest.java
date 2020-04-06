package dev.jedcua.ui.store;

import dev.jedcua.model.Store;
import org.junit.jupiter.api.Test;

public class StoreItemDeleteDialogFactoryTest {
    @Test
    public void build() {
        StoreItemDeleteDialogFactory.build(
            new Store(1L, "Name", "Address", null),
            (dialog) -> {}
        );
    }
}