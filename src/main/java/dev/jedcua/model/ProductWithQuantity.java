package dev.jedcua.model;

import dev.jedcua.ui.invoice.InvoiceProductTableRow;

public final class ProductWithQuantity {
    private final Product product;
    private final int quantity;

    public ProductWithQuantity(final Product product, final int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return this.product.getId();
    }

    public String getName() {
        return this.product.getName();
    }

    public String getBarcode() {
        return this.product.getBarcode();
    }

    public Double getPrice() {
        return this.product.getPrice();
    }

    public String getUnit() {
        return this.product.getUnit();
    }

    public int getQuantity() {
        return this.quantity;
    }

    public static ProductWithQuantity fromTableRow(final InvoiceProductTableRow row) {
        return new ProductWithQuantity(
            row.getProduct(),
            row.getQuantity()
        );
    }
}
