package it.example.lavoretti;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public abstract class BaseTestContainer {

    @LocalServerPort
    private int port;

    /**
     * set the current port of the application to the RestAssured port
     */
    public BaseTestContainer() {
        RestAssured.port = this.port;
    }

    private static final PostgreSQLContainer postgresSQL = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
        .withReuse(true)
        .waitingFor(Wait.forLogMessage(".*Ready to accept connections.*\\n", 1));

    @BeforeAll
    public static void startPostgresSQL() {
        postgresSQL.start();
    }

    // connect spring app to mysql container
    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgresSQL::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgresSQL::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgresSQL::getPassword);
        log.info("postgresSQL started: url = {}, user_name = {} , password ={}", postgresSQL.getJdbcUrl(),
                 postgresSQL.getUsername(),
                 postgresSQL.getPassword());
    }

}
