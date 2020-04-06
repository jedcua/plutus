package dev.jedcua.model;

import java.util.Collections;
import java.util.List;

public class Page<T> {
    private final List<T> items;
    private final int offset;

    public Page(final List<T> items, final int offset) {
        this.items = items;
        this.offset = offset;
    }

    public final List<T> getItems() {
        return items;
    }

    public final int nextOffset() {
        return this.offset + this.items.size();
    }

    public static class Empty<T> extends Page<T> {
        public Empty() {
            super(Collections.emptyList(), 0);
        }
    }
}
