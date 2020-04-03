package dev.jedcua.mock;

import dev.jedcua.db.StoreRepository;
import dev.jedcua.model.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MockStoreRepository implements StoreRepository {
    public final List<Store> stores;

    public MockStoreRepository(final Store... stores) {
        this.stores = new ArrayList<>();
        this.stores.addAll(Arrays.asList(stores));
    }

    @Override
    public List<Store> list() {
        return this.stores;
    }

    @Override
    public void save(Store store) {
        this.stores.add(store);
    }
}
