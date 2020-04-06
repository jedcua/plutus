package dev.jedcua.mock;

import dev.jedcua.db.StoreRepository;
import dev.jedcua.model.Page;
import dev.jedcua.model.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MockStoreRepositoryImpl implements StoreRepository {
    public final List<Store> stores;

    public MockStoreRepositoryImpl(final Store... stores) {
        this.stores = new ArrayList<>();
        this.stores.addAll(Arrays.asList(stores));
    }

    @Override
    public List<Store> list() {
        return this.stores;
    }

    @Override
    public Page<Store> page(int offset, int limit, String search) {
        final List<Store> stores = this.stores.subList(offset, offset + limit);
        return new Page<>(stores, offset);
    }

    @Override
    public void save(Store store) {
        this.stores.add(store);
    }

    @Override
    public void delete(Store store) {
        this.stores.removeIf(
            str -> str.getId().equals(store.getId())
        );
    }
}
