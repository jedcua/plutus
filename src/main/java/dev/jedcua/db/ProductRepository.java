package dev.jedcua.db;

import dev.jedcua.model.Product;
import dev.jedcua.model.Store;

import java.util.List;

public interface ProductRepository {
    List<Product> list(Store store);

    List<Product> search(Store store, String query);

    void save(Store store, Product product);

    void delete(Product product);
}
