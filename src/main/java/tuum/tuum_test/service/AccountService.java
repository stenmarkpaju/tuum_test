package tuum.tuum_test.service;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuum.tuum_test.persistence.mapper.AccountMapper;
import tuum.tuum_test.persistence.model.Account;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AccountService {

    @Autowired private AccountMapper accountMapper;

    public List<Account> findAllAccounts() {

        return accountMapper.findAll();
    }

    public Account findAccountById(UUID accountId) {
        return accountMapper.findAccountByAccountId(accountId);
    }

    //
    //    public Account createAccount(CreateAccountDto accountDto) {
    //        Account account = accountMapper.mapToAccount(accountDto);
    //        accountRepository.insertAccount(account);
    //
    //        if(accountDto.getCurrency().size() > 1){
    //            for (int i = 0; i < accountDto.getCurrency().size(); i++) {
    //                Balance balance = accountMapper.mapToBalance(accountDto, i);
    //                accountRepository.insertBalance(balance);
    //                account.getBalances().add(balance);
    //            }
    //        }
    //
    //        return account;
    //    }
}
