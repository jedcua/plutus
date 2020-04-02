package dev.jedcua.db;

import dev.jedcua.model.Store;

import java.util.List;

public interface StoreRepository {
    List<Store> list();
}
