package dev.jedcua.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class InvoiceTemplate {
    private final Long id;
    private final String name;
    private final String content;

    public InvoiceTemplate(final Long id, final String name, final String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public static InvoiceTemplate fromResultSet(final ResultSet rs) throws SQLException {
        return new InvoiceTemplate(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("content")
        );
    }
}
