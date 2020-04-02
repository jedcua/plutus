package dev.jedcua.config.impl;

import dev.jedcua.config.Configuration;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class EnvironmentConfigurationTest {
    @Test
    public void fetchValues() {
        final Configuration configuration = new EnvironmentConfiguration();
        MatcherAssert.assertThat(
            configuration.dbUrl(),
            Matchers.notNullValue()
        );

        MatcherAssert.assertThat(
            configuration.dbUsername(),
            Matchers.notNullValue()
        );

        MatcherAssert.assertThat(
            configuration.dbPassword(),
            Matchers.notNullValue()
        );
    }
}