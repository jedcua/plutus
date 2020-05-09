package dev.jedcua.ui.product;

import dev.jedcua.model.Product;
import dev.jedcua.utils.FormatUtils;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public final class ProductTableRow {
    private final SimpleLongProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty barcode;
    private final SimpleDoubleProperty price;
    private final SimpleStringProperty unit;

    public ProductTableRow(final Product product) {
        this(
            new SimpleLongProperty(product.getId()),
            new SimpleStringProperty(product.getName()),
            new SimpleStringProperty(product.getBarcode()),
            new SimpleDoubleProperty(product.getPrice()),
            new SimpleStringProperty(product.getUnit())
        );
    }

    public ProductTableRow(
        final SimpleLongProperty id,
        final SimpleStringProperty name,
        final SimpleStringProperty barcode,
        final SimpleDoubleProperty price,
        final SimpleStringProperty unit
    ) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.unit = unit;
    }

    public long getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getBarcode() {
        return barcode.get();
    }

    public double getPrice() {
        return price.get();
    }

    public String getPriceStr() {
        return FormatUtils.formatAmount(this.getPrice());
    }

    public String getUnit() {
        return unit.get();
    }
}
