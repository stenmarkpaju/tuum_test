package tuum.tuum_test.shared;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import tuum.tuum_test.dto.CreateAccountDto;
import tuum.tuum_test.dto.CreateTransactionDto;
import tuum.tuum_test.dto.CreatedTransactionDto;
import tuum.tuum_test.persistence.model.*;

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

    public static Transaction aTransaction(UUID accountId, UUID txid) {
        return Transaction.builder()
                .transactionId(txid)
                .accountId(accountId)
                .transactionDirection(TransactionDirection.IN)
                .amount(20.0)
                .currency(Currency.EUR)
                .description("randomString")
                .build();
    }

    public static CreateTransactionDto aCreateTransactionDto(UUID accountId) {
        return CreateTransactionDto.builder()
                .accountId(accountId)
                .amount(20.0)
                .currency(Currency.EUR)
                .transactionDirection(TransactionDirection.IN)
                .description("randomString")
                .build();
    }

    public static CreateTransactionDto aCreateTransactionDtoWithIncorrectAmount(UUID accountId) {
        return CreateTransactionDto.builder()
                .accountId(accountId)
                .amount(-10.0)
                .currency(Currency.EUR)
                .transactionDirection(TransactionDirection.IN)
                .description("randomString")
                .build();
    }

    public static CreateTransactionDto aCreateTransactionDtoWithInsufficientFundsRequirement(
            UUID accountId) {
        return CreateTransactionDto.builder()
                .accountId(accountId)
                .amount(50.0)
                .currency(Currency.EUR)
                .transactionDirection(TransactionDirection.OUT)
                .description("randomString")
                .build();
    }

    public static Transaction aTransaction(CreateTransactionDto dto, UUID txId) {
        return Transaction.builder()
                .transactionId(txId)
                .accountId(dto.getAccountId())
                .transactionDirection(dto.getTransactionDirection())
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .description(dto.getDescription())
                .build();
    }

    public static Balance addMoneyToBalance(Balance balance, Double addedFunds) {
        return Balance.builder()
                .availableFunds(balance.getAvailableFunds() + addedFunds)
                .currency(balance.getCurrency())
                .build();
    }

    public static CreatedTransactionDto aCreatedTransactionDto(
            Transaction tx, Double leftOnBalance) {
        return CreatedTransactionDto.builder()
                .accountId(tx.getAccountId())
                .transactionId(tx.getTransactionId())
                .amount(tx.getAmount())
                .currency(tx.getCurrency())
                .transactionDirection(tx.getTransactionDirection())
                .description(tx.getDescription())
                .amountLeftOnBalance(leftOnBalance)
                .build();
    }

    public static CreateTransactionDto aCreateTransactionDtoWithoutDescription(UUID accountId) {
        return CreateTransactionDto.builder()
                .accountId(accountId)
                .amount(20.0)
                .currency(Currency.EUR)
                .transactionDirection(TransactionDirection.IN)
                .description("")
                .build();
    }
}
