package dev.jedcua.db.impl;

import dev.jedcua.config.impl.EnvironmentConfiguration;
import dev.jedcua.db.JdbiFactory;
import dev.jedcua.db.StoreRepository;
import dev.jedcua.model.Store;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class StoreRepositoryImplTest {
    private StoreRepository repository;

    @BeforeEach
    public void beforeEach() throws SQLException {
        this.repository = new StoreRepositoryImpl(
            JdbiFactory.initialize(new EnvironmentConfiguration())
        );
    }

    @AfterEach
    public void afterEach() {
        this.repository = null;
    }

    @Test
    public void list() {
        Assertions.assertDoesNotThrow(() -> {
            this.repository.list();
        });
    }

    @Test
    public void saveNew() {
        Assertions.assertDoesNotThrow(() -> {
            this.repository.save(
                new Store(null, "TestStore", "TestStoreAddress", null)
            );
        });
    }

    @Test
    public void saveUpdate() {
        final Store store = this.repository
            .list()
            .stream()
            .findAny()
            .orElseThrow(() ->
                new IllegalStateException("Unable to find any stores for testing!")
            );

        Assertions.assertDoesNotThrow(() -> {
            this.repository.save(store);
        });
    }

    @Test
    public void delete() {
        this.repository.save(
            new Store(null, "TestStore", "TestStoreAddress", null)
        );

        final Store store = this.repository
            .list()
            .stream()
            .findAny()
            .orElseThrow(() ->
                new IllegalStateException("Unable to find any stores for testing!")
            );

        Assertions.assertDoesNotThrow(() -> {
            this.repository.delete(store);
        });
    }
}