package dev.jedcua;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class DependencyManager<T> {
    private static DependencyManager<?> instance;
    private final Map<Class<?>, Object> dependencies;

    private DependencyManager() {
        this.dependencies = new ConcurrentHashMap<>();
    }

    public static DependencyManager initialize() {
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

    public <T> T fetch(final Class<T> clazz) {
        return Optional
            .ofNullable(this.dependencies.get(clazz))
            .map(clazz::cast)
            .orElseThrow(() -> {
                throw new IllegalArgumentException(
                    "Cannot find dependency " + clazz.getCanonicalName()
                );
            });
    }

    public DependencyManager<?> register(final Object object) {
        this.dependencies.put(object.getClass(), object);
        return instance;
    }
}
