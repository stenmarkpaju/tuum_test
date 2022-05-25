package tuum.tuum_test.test.configuration;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainersDatabaseConfiguration
        extends PostgreSQLContainer<TestContainersDatabaseConfiguration> {

    private static final String IMAGE_VERSION = "postgres:11";
    private static TestContainersDatabaseConfiguration container;

    private TestContainersDatabaseConfiguration() {
        super(IMAGE_VERSION);
    }

    public static TestContainersDatabaseConfiguration getInstance() {
        if (container == null) {
            container = new TestContainersDatabaseConfiguration();
        }

        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }
}
