package tuum.tuum_test.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuum.tuum_test.dto.CreateAccountDto;
import tuum.tuum_test.exception.IncorrectCurrencyException;
import tuum.tuum_test.maphelper.AccountMapHelper;
import tuum.tuum_test.persistence.mapper.AccountMapper;
import tuum.tuum_test.persistence.model.Account;
import tuum.tuum_test.persistence.model.Balance;
import tuum.tuum_test.persistence.model.Currency;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AccountService {

    @Autowired private AccountMapHelper accountMapHelper;
    @Autowired private AccountMapper accountMapper;

    public List<Account> findAllAccounts() {

        return accountMapper.findAll();
    }

    public Account findAccountById(UUID accountId) throws Exception {
        Account account = accountMapper.findAccountByAccountId(accountId);

        if (account == null) {
            throw new Exception("Account not found with id " + accountId);
        }

        return accountMapper.findAccountByAccountId(accountId);
    }

    public Account createAccount(CreateAccountDto accountDto) throws IncorrectCurrencyException {
        Account account = accountMapHelper.mapToAccount(accountDto);
        areAllCurrenciesSupported(accountDto.getCurrency());
        accountMapper.insertAccount(account);

        if (accountDto.getCurrency().size() >= 1) {
            for (int i = 0; i < accountDto.getCurrency().size(); i++) {
                if (!EnumUtils.isValidEnum(Currency.class, accountDto.getCurrency().get(i))) {
                    throw new IncorrectCurrencyException(
                            "Given currency is not supported " + accountDto.getCurrency().get(i));
                }
                Balance balance = accountMapHelper.mapToBalance(accountDto, i);
                accountMapper.insertBalance(account.getAccountId(), balance);
                account.getBalance().add(balance);
            }
        }

        return account;
    }

    private void areAllCurrenciesSupported(List<String> currencies)
            throws IncorrectCurrencyException {
        for (String currency : currencies) {
            if (!EnumUtils.isValidEnumIgnoreCase(Currency.class, currency)) {
                throw new IncorrectCurrencyException("Given currency is not supported " + currency);
            }
        }
    }
}
