package dev.jedcua.service.impl;

import dev.jedcua.db.InvoiceTemplateRepository;
import dev.jedcua.model.Invoice;
import dev.jedcua.model.InvoiceTemplate;
import dev.jedcua.service.InvoiceTemplateService;
import dev.jedcua.utils.TemplateUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;

public final class InvoiceTemplateServiceImpl implements InvoiceTemplateService {
    private final InvoiceTemplateRepository templateRepository;

    public InvoiceTemplateServiceImpl(final InvoiceTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public String render(final long templateId, final Invoice invoice) {
        final InvoiceTemplate invoiceTemplate = this.templateRepository.fetch(templateId);

        final VelocityContext context = new VelocityContext();
        context.put("invoice", invoice);
        context.put("Template", TemplateUtils.class);

        final StringWriter stringWriter = new StringWriter();
        Velocity.evaluate(context, stringWriter, invoiceTemplate.getName(), invoiceTemplate.getContent());
        return stringWriter.toString();
    }
}
