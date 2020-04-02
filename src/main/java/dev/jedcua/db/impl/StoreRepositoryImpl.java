package dev.jedcua.db.impl;

import dev.jedcua.db.StoreRepository;
import dev.jedcua.model.Store;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public final class StoreRepositoryImpl implements StoreRepository {
    private static final String SELECT_QUERY = "SELECT id, name, address, tin, created_at, updated_at FROM store";
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
}
