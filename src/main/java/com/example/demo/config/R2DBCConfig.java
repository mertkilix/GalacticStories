package com.example.demo.config;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.time.Duration;

@Configuration
public class R2DBCConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.r2dbc.url}")
    private String dbUrl;

    @Value("${spring.r2dbc.username}")
    private String dbUsername;

    @Value("${spring.r2dbc.password}")
    private String dbPassword;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        String fullDbUrl = String.format("r2dbc:postgresql://%s:%s@%s", dbUsername, dbPassword, dbUrl);
        return ConnectionFactories.get(fullDbUrl);
    }


    @Bean
    public ConnectionPool connectionPool() {
        ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(connectionFactory())
                .maxIdleTime(Duration.ofMinutes(30))
                .maxSize(20)
                .build();
        return new ConnectionPool(configuration);
    }
}