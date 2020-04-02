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

        MatcherAssert.assertThat(
            DependencyManager
                .getInstance()
                .size(),
            Matchers.is(2)
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

        MatcherAssert.assertThat(
            DependencyManager
                .getInstance()
                .size(),
            Matchers.is(2)
        );
    }

    @Test
    public void dependencyInherited() {
        final Dependency1 dep = new Dependency4();

        DependencyManager
            .getInstance()
            .register(dep);

        MatcherAssert.assertThat(
            DependencyManager
                .getInstance()
                .fetch(Dependency1.class),
            Matchers.is(dep)
        );

        MatcherAssert.assertThat(
            DependencyManager
                .getInstance()
                .size(),
            Matchers.is(1)
        );
    }

    @Test
    public void dependencyReplaced() {
        final Dependency1 dependency1 = new Dependency1();
        final Dependency4 dependency4 = new Dependency4();

        DependencyManager
            .getInstance()
            .register(dependency1)
            .register(dependency4);

        MatcherAssert.assertThat(
            DependencyManager
                .getInstance()
                .fetch(Dependency1.class),
            Matchers.is(dependency4)
        );

        MatcherAssert.assertThat(
            DependencyManager
                .getInstance()
                .size(),
            Matchers.is(1)
        );
    }

    @Test
    public void dependencyFromInterface() {
        final Dependency5 dependency6 = new Dependency6();

        DependencyManager
            .getInstance()
            .register(dependency6);

        MatcherAssert.assertThat(
            DependencyManager
                .getInstance()
                .fetch(Dependency5.class),
            Matchers.is(dependency6)
        );

        MatcherAssert.assertThat(
            DependencyManager
                .getInstance()
                .size(),
            Matchers.is(1)
        );
    }

    class Dependency1 { }
    class Dependency2 { }
    class Dependency3 { }
    class Dependency4 extends Dependency1 { }
    interface Dependency5 { }
    class Dependency6 implements Dependency5 { }
}