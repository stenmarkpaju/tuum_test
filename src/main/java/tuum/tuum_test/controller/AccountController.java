package tuum.tuum_test.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuum.tuum_test.dto.CreateAccountDto;
import tuum.tuum_test.dto.GetAccountDto;
import tuum.tuum_test.exception.AccountNotFoundException;
import tuum.tuum_test.exception.IncorrectCurrencyException;
import tuum.tuum_test.maphelper.AccountMapHelper;
import tuum.tuum_test.persistence.model.Account;
import tuum.tuum_test.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;
    private AccountMapHelper accountMapHelper;

    @GetMapping
    public ResponseEntity<List<GetAccountDto>> getAccounts() {
        List<Account> accounts = accountService.findAllAccounts();
        List<GetAccountDto> accountsDto = accountMapHelper.mapToGetAccountDtos(accounts);
        return ResponseEntity.ok(accountsDto);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<GetAccountDto> getAccountById(@PathVariable("accountId") String accountId)
            throws Exception {
        UUID id = UUID.fromString(accountId);
        Account account =
                Optional.of(accountService.findAccountById(id))
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                "Account not found with given id: " + accountId));
        GetAccountDto accountDto = accountMapHelper.mapToGetAccountDto(account);
        return ResponseEntity.ok(accountDto);
    }

    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody CreateAccountDto accountDto)
            throws IncorrectCurrencyException {

        Account createdAccount = accountService.createAccount(accountDto);
        return ResponseEntity.ok(createdAccount);
    }
}
