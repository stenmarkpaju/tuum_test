package tuum.tuum_test.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuum.tuum_test.dto.CreateAccountDto;
import tuum.tuum_test.dto.GetAccountDto;
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
        List<Account> accounts = accountService.findAllChargers();
        List<GetAccountDto> accountsDto = accountMapHelper.mapToGetAccountDtos(accounts);
        return ResponseEntity.ok(accountsDto);
    }

    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody CreateAccountDto accountDto) {
        // Account createdAccount = accountService.createAccount(accountDto);
        // return ResponseEntity.ok(createdAccount);
        return null;
    }
}
