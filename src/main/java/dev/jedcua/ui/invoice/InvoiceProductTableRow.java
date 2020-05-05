package dev.jedcua.ui.invoice;

import dev.jedcua.model.Product;
import javafx.beans.property.*;

public final class InvoiceProductTableRow {
    private final SimpleObjectProperty<Product> product;
    private final SimpleIntegerProperty quantity;

    public InvoiceProductTableRow(final Product product, final int quantity) {
        this(
            new SimpleObjectProperty<Product>(product),
            new SimpleIntegerProperty(quantity)
        );
    }

    public InvoiceProductTableRow(
        final SimpleObjectProperty<Product> product,
        final SimpleIntegerProperty quantity
    ) {
        this.product = product;
        this.quantity = quantity;
    }

    public String getName() {
        return this.product.get().getName();
    }

    public double getPrice() {
        return this.product.get().getPrice();
    }

    public String getUnit() {
        return this.product.get().getUnit();
    }

    public int getQuantity() {
        return this.quantity.get();
    }

    public double getSubtotal() {
        return this.getPrice() * this.getQuantity();
    }

    public Product getProduct() {
        return this.product.get();
    }
}
