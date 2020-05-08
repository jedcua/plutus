package dev.jedcua.service;

import dev.jedcua.model.Invoice;

public interface InvoiceTemplateService {
    String render(long templateId, Invoice invoice);
}
