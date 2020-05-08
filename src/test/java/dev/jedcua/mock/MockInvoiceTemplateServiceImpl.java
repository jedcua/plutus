package dev.jedcua.mock;

import dev.jedcua.model.Invoice;
import dev.jedcua.service.InvoiceTemplateService;

public final class MockInvoiceTemplateServiceImpl implements InvoiceTemplateService {
    private final String template;

    public MockInvoiceTemplateServiceImpl(final String template) {
        this.template = template;
    }

    public MockInvoiceTemplateServiceImpl() {
        this.template = "<html></html>";
    }

    @Override
    public String render(final long templateId, final Invoice invoice) {
        return this.template;
    }
}
