package dev.jedcua.db.impl;

import dev.jedcua.db.StoreRepository;
import dev.jedcua.model.Page;
import dev.jedcua.model.Store;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public final class StoreRepositoryImpl implements StoreRepository {
    private static final String SELECT_QUERY =
        "SELECT id, name, address, tin, created_at, updated_at FROM store ORDER BY name ASC";
    private static final String SELECT_PAGED_QUERY =
        "SELECT id, name, address, tin, created_at, updated_at FROM store "
            + "WHERE ("
                + "LOWER(name) LIKE CONCAT('%', :search, '%') OR "
                + "LOWER(address) LIKE CONCAT('%', :search, '%') OR "
                + ":search IS NULL"
            + ")"
            + "ORDER BY updated_at DESC "
            + "LIMIT :limit OFFSET :offset";
    private static final String SAVE_COMMAND =
        "INSERT INTO store(id, name, address, tin) VALUES (:id, :name, :address, :tin)";
    private static final String UPDATE_COMMAND =
        "UPDATE store SET name = :name, address = :address, tin = :tin WHERE id = :id";
    private static final String DELETE_COMMAND = "DELETE FROM store WHERE id = :id";
    private final Jdbi jdbi;

    public StoreRepositoryImpl(final Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public List<Store> list() {
        return this.jdbi
            .withHandle(handle -> handle
                .createQuery(SELECT_QUERY)
                .map((rs, ctx) -> Store.fromResultSet(rs))
                .list());
    }

    @Override
    public Page<Store> page(final int offset, final int limit, final String search) {
        final List<Store> stores = this.jdbi
            .withHandle(handle -> handle
                .createQuery(SELECT_PAGED_QUERY)
                .bind("offset", offset)
                .bind("limit", limit)
                .bind("search", search)
                .map((rs, ctx) -> Store.fromResultSet(rs))
                .list());
        return new Page<>(stores, offset);
    }

    @Override
    public void save(final Store store) {
        if (store.getId() == null) {
            this.insert(store);
        } else {
            this.update(store);
        }
    }

    @Override
    public void delete(final Store store) {
        this.jdbi.useHandle(handle -> handle
            .createUpdate(DELETE_COMMAND)
            .bind("id", store.getId())
            .execute()
        );
    }

    public void insert(final Store store) {
        this.jdbi.useHandle(handle -> {
            handle
                .createUpdate(SAVE_COMMAND)
                .bind("id", store.getId())
                .bind("name", store.getName())
                .bind("address", store.getAddress())
                .bind("tin", store.getTin())
                .execute();
        });
    }

    public void update(final Store store) {
        this.jdbi.useHandle(handle -> {
            handle
                .createUpdate(UPDATE_COMMAND)
                .bind("id", store.getId())
                .bind("name", store.getName())
                .bind("address", store.getAddress())
                .bind("tin", store.getTin())
                .execute();
        });
    }
}
