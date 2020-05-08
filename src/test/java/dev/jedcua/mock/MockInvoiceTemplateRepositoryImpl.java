package dev.jedcua.mock;

import dev.jedcua.db.InvoiceTemplateRepository;
import dev.jedcua.model.InvoiceTemplate;

public final class MockInvoiceTemplateRepositoryImpl implements InvoiceTemplateRepository  {
    private final String template;

    public MockInvoiceTemplateRepositoryImpl(final String template) {
        this.template = template;
    }

    @Override
    public InvoiceTemplate fetch(final long id) {
        return new InvoiceTemplate(id, "name", this.template);
    }
}
