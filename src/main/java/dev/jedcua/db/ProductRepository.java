package dev.jedcua.db;

import dev.jedcua.model.Product;
import dev.jedcua.model.Store;

import java.util.List;

public interface ProductRepository {
    List<Product> list(Store store);

    void save(Store store, Product product);
}
