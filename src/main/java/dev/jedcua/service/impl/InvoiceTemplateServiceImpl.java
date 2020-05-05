package dev.jedcua.service.impl;

import dev.jedcua.model.Invoice;
import dev.jedcua.service.InvoiceTemplateService;

public final class InvoiceTemplateServiceImpl implements InvoiceTemplateService {
    @Override
    public String render(final Invoice invoice) {
        return String.format(
            "<html>"
                + "<body>"
                + "<center>"
                + "<h1> %s </h1>"
                + "<p> Products: %s </p>"
                + "<p> Delivery: %s </p>"
                + "</center>"
                + "</body>"
                + "</html>",
            invoice.getStore().getName(),
            invoice.getProducts().size(),
            invoice.getDeliveryDate()
        );
    }
}
