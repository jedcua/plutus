package dev.jedcua.db;

import dev.jedcua.model.InvoiceTemplate;

public interface InvoiceTemplateRepository {
    InvoiceTemplate fetch(long id);
}
