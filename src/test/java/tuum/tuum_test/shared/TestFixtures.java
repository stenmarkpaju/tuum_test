package tuum.tuum_test.shared;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import tuum.tuum_test.dto.CreateAccountDto;
import tuum.tuum_test.persistence.model.Account;
import tuum.tuum_test.persistence.model.Balance;
import tuum.tuum_test.persistence.model.Currency;

public class TestFixtures {

    public static Account.AccountBuilder anAccountWithId(UUID id) {
        return Account.builder().accountId(id).customerId("test");
    }

    public static Account anAccountWithBalance(UUID id) {
        return anAccountWithId(id).balance(List.of(aBalance())).build();
    }

    public static Balance aBalance() {
        return Balance.builder().currency(Currency.EUR).availableFunds(20.0).build();
    }

    public static CreateAccountDto aCreateAccountDto() {
        return CreateAccountDto.builder()
                .customerId("customerId")
                .country("someCountry")
                .currency(Collections.singletonList("EUR"))
                .build();
    }

    public static Account anAccountWithIdAndCustomerIdAndCountry(
            UUID id, String customerId, String country) {
        return Account.builder().accountId(id).customerId(customerId).country(country).build();
    }

    public static Balance aBalanceWithCurrencyAndAvailableFunds(Currency currency, Double funds) {
        return Balance.builder().currency(currency).availableFunds(funds).build();
    }
}
