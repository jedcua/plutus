package dev.jedcua;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DependencyManagerTest {
    @BeforeEach
    public void beforeEach() {
        DependencyManager.initialize();
    }

    @AfterEach
    public void afterAll() {
        DependencyManager.destroy();
    }

    @Test
    public void notInitialized() {
        DependencyManager.destroy();
        Assertions.assertThrows(
            IllegalStateException.class,
            DependencyManager::getInstance
        );
    }

    @Test
    public void registerAndFetch() {
        final Dependency1 dependency1 = new Dependency1();
        final Dependency2 dependency2 = new Dependency2();

        DependencyManager
            .getInstance()
            .register(dependency1)
            .register(dependency2);

        MatcherAssert.assertThat(
            DependencyManager
                .getInstance()
                .fetch(Dependency1.class),
            Matchers.is(dependency1)
        );

        MatcherAssert.assertThat(
            DependencyManager
                .getInstance()
                .fetch(Dependency2.class),
            Matchers.is(dependency2)
        );
    }

    @Test
    public void dependencyNotFound() {
        final Dependency1 dependency1 = new Dependency1();
        final Dependency2 dependency2 = new Dependency2();

        DependencyManager
            .getInstance()
            .register(dependency1)
            .register(dependency2);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            DependencyManager
                .getInstance()
                .fetch(Dependency3.class);
        });
    }

    class Dependency1 { }
    class Dependency2 { }
    class Dependency3 { }
}