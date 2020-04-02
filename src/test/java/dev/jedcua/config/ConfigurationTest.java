package dev.jedcua.config;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class ConfigurationTest {
    @Test
    public void defaultsNull() {
        final Configuration configuration = () -> "url";
        MatcherAssert.assertThat(
            configuration.dbUsername(),
            Matchers.nullValue()
        );
        MatcherAssert.assertThat(
            configuration.dbPassword(),
            Matchers.nullValue()
        );
    }
}