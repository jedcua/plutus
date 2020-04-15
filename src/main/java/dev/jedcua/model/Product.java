package dev.jedcua.model;

import dev.jedcua.ui.product.ProductTableRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class Product {
    private final Long id;
    private final String name;
    private final String barcode;
    private final Double price;
    private final String unit;

    public Product(final Long id, final String name, final String barcode, final Double price, final String unit) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public Double getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public static Product fromResultSet(final ResultSet rs) throws SQLException {
        return new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("barcode"),
            rs.getDouble("price"),
            rs.getString("unit")
        );
    }

    public static Product fromTableRow(final ProductTableRow tableRow) {
        return new Product(
            tableRow.getId(),
            tableRow.getName(),
            tableRow.getBarcode(),
            tableRow.getPrice(),
            tableRow.getUnit()
        );
    }

    public static Product empty() {
        return new Product(
            null,
            null,
            null,
            null,
            null
        );
    }
}
