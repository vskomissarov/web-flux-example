package ru.filit.backend.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import static io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_SIZE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DRIVER;
import static io.r2dbc.spi.ConnectionFactoryOptions.HOST;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.PORT;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

@Setter
@Getter
@Configuration
@EnableR2dbcRepositories
public class R2DBCConfig extends AbstractR2dbcConfiguration {

    private final String POSTGRE_SQL = "postgresql";
    private final String LOCALHOST = "localhost";
    private final String DATABASE_NAME = "backend";
    private final int PORT_NUMBER = 5432;
    private final int MAX_CONNECTION_SIZE = 40;

    @Value("${spring.r2dbc.password}")
    private String password;
    @Value("${spring.r2dbc.username}")
    private String username;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(DRIVER, POSTGRE_SQL)
                        .option(HOST, LOCALHOST)
                        .option(PORT, PORT_NUMBER)
                        .option(USER, username)
                        .option(PASSWORD, password)
                        .option(DATABASE, DATABASE_NAME)
                        .option(MAX_SIZE, MAX_CONNECTION_SIZE)
                        .build());
    }
}