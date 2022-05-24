package tuum.tuum_test.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tuum.tuum_test.dto.CreateTransactionDto;
import tuum.tuum_test.dto.CreatedTransactionDto;
import tuum.tuum_test.exception.*;
import tuum.tuum_test.maphelper.TransactionMapHelper;
import tuum.tuum_test.persistence.mapper.AccountMapper;
import tuum.tuum_test.persistence.mapper.TransactionMapper;
import tuum.tuum_test.persistence.model.Balance;
import tuum.tuum_test.persistence.model.Currency;
import tuum.tuum_test.persistence.model.Transaction;
import tuum.tuum_test.persistence.model.TransactionDirection;

@Service
@Slf4j
public class TransactionService {

    @Autowired private TransactionMapper transactionMapper;
    @Autowired private AccountMapper accountMapper;

    @Autowired TransactionMapHelper transactionMapHelper;

    public List<Transaction> findTransactionsByAccountId(UUID accountId) {
        return Optional.ofNullable(transactionMapper.findTransactionsByAccountId(accountId))
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        "No transactions found for account " + accountId));
    }

    public CreatedTransactionDto createTransaction(CreateTransactionDto createTransaction)
            throws InsufficientFundsException, AccountNotFoundException,
                    InvalidMonetaryAmountException, IncorrectCurrencyException,
                    InvalidDescriptionException {
        if (createTransaction.getDescription().isEmpty()) {
            throw new InvalidDescriptionException("The description provided by the user is empty");
        }

        Transaction transaction = transactionMapHelper.mapToTransaction(createTransaction);
        List<Balance> balances =
                Optional.of(accountMapper.getAllBalances(transaction.getAccountId()))
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                "Account not found with id "
                                                        + transaction.getAccountId()));

        Balance balanceToUpdate =
                findBalanceToUpdateByCurrency(balances, transaction.getCurrency());
        balanceToUpdate.setAvailableFunds(
                doCalculationBasedOnTransactionDirection(
                        balanceToUpdate.getAvailableFunds(),
                        transaction.getTransactionDirection(),
                        transaction.getAmount()));

        accountMapper.updateBalanceByAccountIdAndCurrency(
                transaction.getAccountId(), balanceToUpdate);
        transactionMapper.insertTransaction(transaction);

        return transactionMapHelper.mapToCreatedTransactionDto(
                transaction, balanceToUpdate.getAvailableFunds());
    }

    private boolean isCurrencyOfValidValue(String currency) {
        return EnumUtils.isValidEnumIgnoreCase(Currency.class, currency);
    }

    private Balance findBalanceToUpdateByCurrency(List<Balance> balances, Currency currency)
            throws IncorrectCurrencyException {
        if (!isCurrencyOfValidValue(currency.name())) {
            throw new IncorrectCurrencyException(
                    "Currency inserted by the user is not valid: " + currency.name());
        }

        return balances.stream()
                .filter(balance -> currency.equals(balance.getCurrency()))
                .findFirst()
                .orElse(null);
    }

    private Double doCalculationBasedOnTransactionDirection(
            Double availableFunds, TransactionDirection txDirection, Double transactionAmount)
            throws InsufficientFundsException, InvalidMonetaryAmountException {

        if (transactionAmount < 0) {
            throw new InvalidMonetaryAmountException(
                    "Transaction amount input from the user for the transaction is less than 0: "
                            + transactionAmount);
        }

        return txDirection == TransactionDirection.IN
                ? availableFunds + transactionAmount
                : doCalculationForOutgoingTx(availableFunds, transactionAmount);
    }

    private Double doCalculationForOutgoingTx(Double availableFunds, Double transactionAmount)
            throws InsufficientFundsException {
        var outcome = availableFunds - transactionAmount;
        if (outcome >= 0) {
            return outcome;
        }
        throw new InsufficientFundsException("Transaction was unsuccessful. Insufficient funds.");
    }
}
