package uz.duol.ecopharmwarehouse.common;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Arrays;

public class LocalTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String POSTGRESQL_TAG = "postgres:16-alpine";
    private static final DockerImageName POSTGRESQL_IMAGE = DockerImageName.parse(POSTGRESQL_TAG);
    @Container
    private static final PostgreSQLContainer POSTGRESQL_CONTAINER = new PostgreSQLContainer(POSTGRESQL_IMAGE);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!Arrays.asList(applicationContext.getEnvironment().getActiveProfiles()).contains("prod-test")) {
            startWithLocalMachineProperties(applicationContext);
        }
    }

    private void startWithLocalMachineProperties(ConfigurableApplicationContext applicationContext) {
        POSTGRESQL_CONTAINER
                .withUsername("postgres")
                .withPassword("postgres")
                .withDatabaseName("postgres")
                .withStartupTimeout(Duration.ofMinutes(10))
                .withReuse(true);

        Startables.deepStart(POSTGRESQL_CONTAINER).join();


        System.out.println("JDBC_URL: " + POSTGRESQL_CONTAINER.getJdbcUrl());
        System.out.println("JDBC_URL_USERNAME: " + POSTGRESQL_CONTAINER.getUsername());
        System.out.println("JDBC_URL_PASSWORD: " + POSTGRESQL_CONTAINER.getPassword());

        TestPropertyValues values = TestPropertyValues.of(
                "spring.datasource.url:" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                "spring.datasource.username:" + POSTGRESQL_CONTAINER.getUsername(),
                "spring.datasource.password:" + POSTGRESQL_CONTAINER.getPassword()
        );
        values.applyTo(applicationContext);
    }
}
