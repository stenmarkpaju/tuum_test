package tuum.tuum_test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static tuum.tuum_test.shared.TestFixtures.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tuum.tuum_test.dto.CreateAccountDto;
import tuum.tuum_test.exception.AccountNotFoundException;
import tuum.tuum_test.exception.IncorrectCurrencyException;
import tuum.tuum_test.maphelper.AccountMapHelper;
import tuum.tuum_test.persistence.mapper.AccountMapper;
import tuum.tuum_test.persistence.model.Account;
import tuum.tuum_test.persistence.model.Currency;

@ExtendWith(MockitoExtension.class)
@Suite
public class AccountServiceTest {

    @Mock AccountMapHelper accountMapHelper;
    @Mock AccountMapper accountMapper;

    @InjectMocks AccountService accountService;

    private final UUID accountId = UUID.randomUUID();

    @Test
    void whenGetAllAccounts_thenReturnAccounts() {
        List<Account> accounts = Collections.singletonList(anAccountWithBalance(accountId));

        when(accountMapper.findAll()).thenReturn(accounts);

        List<Account> actual = accountMapper.findAll();

        assertEquals(actual, accounts);
    }

    @Test
    void whenFindAccountById_returnsAccountWithGivenId() {
        Account account = anAccountWithBalance(accountId);
        when(accountMapper.findAccountByAccountId(accountId)).thenReturn(account);

        Account actual = accountMapper.findAccountByAccountId(accountId);

        assertEquals(actual, account);
    }

    @Test
    void whenFindAccountById_thenReturnsAccountNotFoundException() {
        Assertions.assertThrows(
                AccountNotFoundException.class,
                () -> accountService.findAccountById(UUID.randomUUID()));
    }

    @Test
    void whenCreateAccount_returnsAccount() throws IncorrectCurrencyException {
        CreateAccountDto createAccountDto = aCreateAccountDto();
        when(accountMapHelper.mapToAccount(createAccountDto))
                .thenReturn(
                        anAccountWithIdAndCustomerIdAndCountry(
                                accountId,
                                createAccountDto.getCustomerId(),
                                createAccountDto.getCountry()));
        when(accountMapHelper.mapToBalance(createAccountDto, 0))
                .thenReturn(
                        aBalanceWithCurrencyAndAvailableFunds(
                                Currency.valueOf(createAccountDto.getCurrency().get(0)), 20.0));

        when(accountMapper.insertAccount(any())).thenReturn(1);
        when(accountMapper.insertBalance(any(), any())).thenReturn(1);

        Account actual = accountService.createAccount(createAccountDto);

        assertEquals(createAccountDto.getCustomerId(), actual.getCustomerId());
        assertEquals(createAccountDto.getCountry(), actual.getCountry());
        assertEquals(
                Currency.valueOf(createAccountDto.getCurrency().get(0)),
                actual.getBalance().get(0).getCurrency());
    }
}
