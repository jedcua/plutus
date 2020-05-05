package dev.jedcua.model;

import java.time.LocalDate;
import java.util.List;

public final class Invoice {
    private final Store store;
    private final List<ProductWithQuantity> products;
    private final LocalDate deliveryDate;

    public Invoice(
        final Store store,
        final List<ProductWithQuantity> products,
        final LocalDate deliveryDate
    ) {
        this.store = store;
        this.products = products;
        this.deliveryDate = deliveryDate;
    }

    public Store getStore() {
        return store;
    }

    public List<ProductWithQuantity> getProducts() {
        return products;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
}
