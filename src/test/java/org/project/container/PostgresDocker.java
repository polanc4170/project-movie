package org.project.container;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostgresDocker {

	@SuppressWarnings("rawtypes")
	@Container
	private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest");

	@DynamicPropertySource
	public static void overrideProperties (DynamicPropertyRegistry registry) {
		registry.add("spring.test.database.replace", () -> "none");
		registry.add("spring.datasource.url",        container::getJdbcUrl);
		registry.add("spring.datasource.username",   container::getUsername);
		registry.add("spring.datasource.password",   container::getPassword);
	}

	@BeforeAll
	public static void beforeAll () {
		container.start();
	}

}
