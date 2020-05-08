package dev.jedcua.db.impl;

import dev.jedcua.db.InvoiceTemplateRepository;
import dev.jedcua.model.InvoiceTemplate;
import org.jdbi.v3.core.Jdbi;

public final class InvoiceTemplateRepositoryImpl implements InvoiceTemplateRepository {
    public static final String SELECT_WHERE_ID =
        "SELECT id, name, content FROM invoice_template WHERE id = :id";
    private final Jdbi jdbi;

    public InvoiceTemplateRepositoryImpl(final Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public InvoiceTemplate fetch(final long id) {
        return this.jdbi.withHandle(handle -> handle
            .createQuery(SELECT_WHERE_ID)
            .bind("id", id)
            .map((rs, ctx) -> InvoiceTemplate.fromResultSet(rs))
            .one());
    }
}
