package dev.jedcua.model;

import java.util.Collections;
import java.util.List;

public final class Page<T> {
    private final List<T> items;
    private final int offset;

    public Page(final List<T> items, final int offset) {
        this.items = items;
        this.offset = offset;
    }

    public Page(final int offset) {
        this(Collections.emptyList(), offset);
    }

    public List<T> getItems() {
        return items;
    }

    public int nextOffset() {
        return this.offset + this.items.size();
    }
}
