package dev.jedcua.mock;

import dev.jedcua.db.ProductRepository;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class MockProductRepositoryImpl implements ProductRepository {
    private final List<Product> products;

    public MockProductRepositoryImpl(final Product... products) {
        this.products = new ArrayList<>();
        this.products.addAll(Arrays.asList(products));
    }

    @Override
    public List<Product> list(Store store) {
        return this.products;
    }

    @Override
    public List<Product> search(final Store store, final String query) {
        return this.products
            .stream()
            .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public void save(Store store, Product product) {
        this.products.add(product);
    }

    @Override
    public void delete(Product product) {
        this.products.removeIf(
            p -> p.getId().equals(product.getId())
        );
    }
}
