package dev.jedcua.db.impl;

import dev.jedcua.db.ProductRepository;
import dev.jedcua.model.Product;
import dev.jedcua.model.Store;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public final class ProductRepositoryImpl implements ProductRepository {
    private static final String SELECT_QUERY =
        "SELECT id, name, barcode, price, unit, created_at, updated_at FROM product WHERE store_id = :store_id";
    private static final String INSERT_COMMAND =
        "INSERT INTO "
            + "product (name, barcode, price, unit, store_id) "
            + "VALUES (:name, :barcode, :price, :unit, :store_id)";
    private static final String UPDATE_COMMAND =
        "UPDATE product SET "
            + "name = :name, "
            + "barcode = :barcode, "
            + "price = :price, "
            + "unit = :unit "
            + "WHERE id = :id";
    private static final String DELETE_COMMAND = "DELETE FROM product WHERE id = :id";
    private final Jdbi jdbi;

    public ProductRepositoryImpl(final Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public List<Product> list(final Store store) {
        return this.jdbi
            .withHandle(handle -> handle
                .createQuery(SELECT_QUERY)
                .bind("store_id", store.getId())
                .map((rs, ctx) -> Product.fromResultSet(rs))
                .list());
    }

    @Override
    public void save(final Store store, final Product product) {
        if (product.getId() == null) {
            this.insert(store, product);
        } else {
            this.update(product);
        }
    }

    @Override
    public void delete(final Product product) {
        this.jdbi.useHandle(handle -> handle
            .createUpdate(DELETE_COMMAND)
            .bind("id", product.getId())
            .execute()
        );
    }

    private void insert(final Store store, final Product product) {
        this.jdbi.useHandle(handle -> handle
            .createUpdate(INSERT_COMMAND)
            .bind("store_id", store.getId())
            .bind("name", product.getName())
            .bind("barcode", product.getBarcode())
            .bind("price", product.getPrice())
            .bind("unit", product.getUnit())
            .execute()
        );
    }

    private void update(final Product product) {
        this.jdbi.useHandle(handle -> handle
            .createUpdate(UPDATE_COMMAND)
            .bind("id", product.getId())
            .bind("name", product.getName())
            .bind("barcode", product.getBarcode())
            .bind("price", product.getPrice())
            .bind("unit", product.getUnit())
            .execute()
        );
    }
}
