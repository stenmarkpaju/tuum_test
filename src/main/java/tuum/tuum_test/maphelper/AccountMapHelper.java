package tuum.tuum_test.maphelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import tuum.tuum_test.dto.CreateAccountDto;
import tuum.tuum_test.dto.GetAccountDto;
import tuum.tuum_test.persistence.model.Account;
import tuum.tuum_test.persistence.model.Balance;

@Component
public class AccountMapHelper {

    public Account mapAccountDtoToAccount(CreateAccountDto accountDto) {
        return Account.builder().customerId(accountDto.getCustomerId()).build();
    }

    public List<GetAccountDto> mapToGetAccountDtos(List<Account> accounts) {
        List<GetAccountDto> dtos = new ArrayList<>();
        accounts.forEach(account -> dtos.add(mapToGetAccountDto(account)));

        return dtos;
    }

    public GetAccountDto mapToGetAccountDto(Account account) {
        return GetAccountDto.builder()
                .accountId(account.getAccountId())
                .customerId(account.getCustomerId())
                .balances(account.getBalance())
                .build();
    }

    public Account mapToAccount(CreateAccountDto createAccountDto) {
        return Account.builder()
                .accountId(UUID.randomUUID())
                .customerId(createAccountDto.getCustomerId())
                .country(createAccountDto.getCountry())
                .build();
    }

    public Balance mapToBalance(CreateAccountDto createAccountDto, int iterable) {
        return Balance.builder()
                .availableFunds(0.0)
                .currency(createAccountDto.getCurrency().get(iterable))
                .build();
    }
}
