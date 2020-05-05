package dev.jedcua.service.impl;

import dev.jedcua.model.Invoice;
import dev.jedcua.model.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

public class InvoiceTemplateServiceImplTest {
    @Test
    public void render() {
        Assertions.assertDoesNotThrow(() -> {
            new InvoiceTemplateServiceImpl().render(
                new Invoice(
                    new Store(1L, "Name", "Address", "Tin"),
                    Collections.emptyList(),
                    LocalDate.now()
                )
            );
        });
    }
}