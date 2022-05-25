package tuum.tuum_test.service;

import static org.junit.jupiter.api.Assertions.*;
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
import tuum.tuum_test.dto.CreateTransactionDto;
import tuum.tuum_test.exception.*;
import tuum.tuum_test.maphelper.TransactionMapHelper;
import tuum.tuum_test.persistence.mapper.AccountMapper;
import tuum.tuum_test.persistence.mapper.TransactionMapper;
import tuum.tuum_test.persistence.model.Balance;
import tuum.tuum_test.persistence.model.Transaction;
import tuum.tuum_test.shared.TestFixtures;

@ExtendWith(MockitoExtension.class)
@Suite
public class TransactionServiceTest {

    @Mock TransactionMapper transactionMapper;
    @Mock AccountMapper accountMapper;
    @Mock TransactionMapHelper transactionMapHelper;

    @InjectMocks TransactionService transactionService;

    private final UUID accountId = UUID.randomUUID();
    private final UUID txId = UUID.randomUUID();

    @Test
    void whenFindByTransactionsByAccountId_returnsListOfTransactions() {
        List<Transaction> transactions = Collections.singletonList(aTransaction(accountId, txId));

        when(transactionMapper.findTransactionsByAccountId(accountId)).thenReturn(transactions);

        List<Transaction> actual = transactionMapper.findTransactionsByAccountId(accountId);

        assertEquals(transactions.get(0).getTransactionId(), actual.get(0).getTransactionId());
        assertEquals(transactions.get(0).getAccountId(), actual.get(0).getAccountId());
    }

    @Test
    void whenFindByTransactionsByAccountId_throwTransactionNotFoundException() {
        when(transactionMapper.findTransactionsByAccountId(accountId)).thenReturn(null);

        Assertions.assertThrows(
                TransactionNotFoundException.class,
                () -> transactionService.findTransactionsByAccountId(accountId));
    }

    @Test
    void whenCreateTransaction_thenReturnsCreatedTransactionDto()
            throws InsufficientFundsException, InvalidMonetaryAmountException,
                    InvalidDescriptionException, AccountNotFoundException {
        CreateTransactionDto createTransactionDto = TestFixtures.aCreateTransactionDto(accountId);
        Balance updatedBalance = addMoneyToBalance(aBalance(), createTransactionDto.getAmount());
        Transaction tx = aTransaction(createTransactionDto, txId);

        when(transactionMapHelper.mapToTransaction(createTransactionDto)).thenReturn(tx);
        when(accountMapper.getAllBalances(accountId))
                .thenReturn(Collections.singletonList(aBalance()));
        when(accountMapper.updateBalanceByAccountIdAndCurrency(
                        createTransactionDto.getAccountId(), updatedBalance))
                .thenReturn(1);
        when(transactionMapper.insertTransaction(tx)).thenReturn(1);
        when(transactionMapHelper.mapToCreatedTransactionDto(
                        tx, updatedBalance.getAvailableFunds()))
                .thenReturn(aCreatedTransactionDto(tx, updatedBalance.getAvailableFunds()));

        var actual = transactionService.createTransaction(createTransactionDto);

        assertEquals(createTransactionDto.getAccountId(), actual.getAccountId());
        assertEquals(txId, actual.getTransactionId());
    }

    @Test
    void whenCreateTransaction_throwInvalidDescriptionException() {
        Assertions.assertThrows(
                InvalidDescriptionException.class,
                () ->
                        transactionService.createTransaction(
                                aCreateTransactionDtoWithoutDescription(accountId)));
    }

    @Test
    void createTransaction_throwsAccountNotFoundException() {
        CreateTransactionDto createTransactionDto = aCreateTransactionDto(accountId);

        when(transactionMapHelper.mapToTransaction(createTransactionDto))
                .thenReturn(aTransaction(createTransactionDto, txId));
        when(accountMapper.getAllBalances(accountId)).thenReturn(null);

        Assertions.assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.createTransaction(aCreateTransactionDto(accountId)));
    }

    @Test
    void createTransaction_throwsInvalidMonetaryAmountException() {
        CreateTransactionDto createTransactionDto =
                aCreateTransactionDtoWithIncorrectAmount(accountId);

        when(transactionMapHelper.mapToTransaction(createTransactionDto))
                .thenReturn(aTransaction(createTransactionDto, txId));
        when(accountMapper.getAllBalances(accountId))
                .thenReturn(Collections.singletonList(aBalance()));

        Assertions.assertThrows(
                InvalidMonetaryAmountException.class,
                () ->
                        transactionService.createTransaction(
                                aCreateTransactionDtoWithIncorrectAmount(accountId)));
    }

    @Test
    void createTransaction_throwsInsufficientFunds() {
        CreateTransactionDto createTransactionDto =
                aCreateTransactionDtoWithInsufficientFundsRequirement(accountId);

        when(transactionMapHelper.mapToTransaction(createTransactionDto))
                .thenReturn(aTransaction(createTransactionDto, txId));
        when(accountMapper.getAllBalances(accountId))
                .thenReturn(Collections.singletonList(aBalance()));

        Assertions.assertThrows(
                InsufficientFundsException.class,
                () ->
                        transactionService.createTransaction(
                                aCreateTransactionDtoWithInsufficientFundsRequirement(accountId)));
    }
}
