package dev.jedcua.db.impl;

import dev.jedcua.config.impl.EnvironmentConfiguration;
import dev.jedcua.db.JdbiFactory;
import dev.jedcua.db.ProductRepository;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductRepositoryImplTest {
    private Store store;
    private ProductRepository productRepository;

    @BeforeEach
    public void beforeEach() throws SQLException {
        this.store = new StoreRepositoryImpl(JdbiFactory.initialize(new EnvironmentConfiguration()))
            .list()
            .stream()
            .findFirst()
            .orElseThrow(
                () -> new IllegalStateException("No stores to test!")
            );
        this.productRepository = new ProductRepositoryImpl(
            JdbiFactory.initialize(new EnvironmentConfiguration())
        );
    }

    @AfterEach
    public void afterEach() {
        this.store = null;
        this.productRepository = null;
    }

    @Test
    public void save() {
        Assertions.assertDoesNotThrow(() -> {
            this.productRepository.save(store, new Product(
                null, "Name", "Barcode", 123.45, "Unit")
            );
        });
    }

    @Test
    public void list() {
        final String name = UUID.randomUUID().toString();
        final Product product = new Product(
            null,
            name,
            "ProductBarCode",
            123.45,
            "Unit"
        );
        this.productRepository.save(store, product);

        MatcherAssert.assertThat(
            this.productRepository
                .list(store)
                .stream()
                .map(Product::getName)
                .collect(Collectors.toList()),
            Matchers.contains(name)
        );
    }
}