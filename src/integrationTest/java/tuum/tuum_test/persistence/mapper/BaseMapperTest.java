package tuum.tuum_test.persistence.mapper;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import tuum.tuum_test.test.configuration.TestContainersDatabaseConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaseMapperTest {

    @ClassRule
    public static final PostgreSQLContainer<?> postgreSQLContainer =
            TestContainersDatabaseConfiguration.getInstance();
}
