package dev.jedcua.service.impl;

import dev.jedcua.config.impl.EnvironmentConfiguration;
import dev.jedcua.db.JdbiFactory;
import dev.jedcua.db.impl.InvoiceTemplateRepositoryImpl;
import dev.jedcua.model.Invoice;
import dev.jedcua.model.Product;
import dev.jedcua.model.ProductWithQuantity;
import dev.jedcua.model.Store;
import dev.jedcua.service.InvoiceTemplateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

public class InvoiceTemplateServiceImplTest {
    private InvoiceTemplateService service;

    @BeforeEach
    public void beforeEach() throws SQLException {
        this.service = new InvoiceTemplateServiceImpl(
            new InvoiceTemplateRepositoryImpl(
                JdbiFactory.initialize(new EnvironmentConfiguration())
            )
        );
    }

    @Test
    public void render() {
        Assertions.assertDoesNotThrow(() -> {
            service.render(
                1L,
                new Invoice(
                    new Store(1L, "Name", "Address", "Tin"),
                    Arrays.asList(
                        new ProductWithQuantity(
                            new Product(1L, "ProductName", "ProductBarcode", 123.456, "pcs" ),
                            10
                        ),
                        new ProductWithQuantity(
                            new Product(2L, "ProductName", "ProductBarcode", 123.456, "pcs" ),
                            20
                        )
                    ),
                    LocalDate.now()
                )
            );
        });
    }
}