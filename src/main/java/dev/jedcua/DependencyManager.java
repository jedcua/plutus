package dev.jedcua;

import java.util.ArrayList;
import java.util.List;

public final class DependencyManager<T> {
    private static DependencyManager<?> instance;
    private final List<Object> dependencies;

    private DependencyManager() {
        this.dependencies = new ArrayList<>();
    }

    public static DependencyManager<?> initialize() {
        instance = new DependencyManager<>();
        return instance;
    }

    public static void destroy() {
        instance = null;
    }

    public static DependencyManager<?> getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                DependencyManager.class.getName() + " is null!"
            );
        }
        return instance;
    }

    public <U> U fetch(final Class<U> clazz) {
        return this.dependencies
            .stream()
            .filter(obj -> clazz.isAssignableFrom(obj.getClass()))
            .findAny()
            .map(clazz::cast)
            .orElseThrow(() -> {
                throw new IllegalArgumentException(
                    "Cannot find dependency " + clazz.getCanonicalName()
                );
            });
    }

    public DependencyManager<?> register(final Object object) {
        Object dependency = null;

        for (int idx = 0; idx < this.dependencies.size(); idx = idx + 1) {
            final Object current = this.dependencies.get(idx);
            if (current.getClass().isAssignableFrom(object.getClass())) {
                dependency = current;
                this.dependencies.set(idx, object);
                break;
            }
        }

        if (dependency == null) {
            this.dependencies.add(object);
        }
        return instance;
    }

    public int size() {
        return this.dependencies.size();
    }
}
