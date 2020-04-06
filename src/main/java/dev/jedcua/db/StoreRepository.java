package dev.jedcua.db;

import dev.jedcua.model.Page;
import dev.jedcua.model.Store;

import java.util.List;

public interface StoreRepository {
    List<Store> list();

    Page<Store> page(int offset, int limit, String search);

    void save(Store store);
}
