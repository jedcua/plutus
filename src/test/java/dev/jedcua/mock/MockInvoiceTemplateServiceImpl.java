package dev.jedcua.mock;

import dev.jedcua.model.Invoice;
import dev.jedcua.service.InvoiceTemplateService;

public final class MockInvoiceTemplateServiceImpl implements InvoiceTemplateService {
    @Override
    public String render(final Invoice invoice) {
        return "<html></html>";
    }
}
