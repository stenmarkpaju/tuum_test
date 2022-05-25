package tuum.tuum_test.persistence.mapper;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import tuum.tuum_test.persistence.model.Account;

public class AccountMapperTest extends BaseMapperTest {

    @Autowired private AccountMapper accountMapper;

    @Test
    @Sql("/test-sql/account_balance-insert.sql")
    public void getAllAccounts() {
        List<Account> accounts = accountMapper.findAll();

        assertThat(accounts).hasSize(3);
    }
}
