package tuum.tuum_test.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuum.tuum_test.dto.CreateTransactionDto;
import tuum.tuum_test.maphelper.TransactionMapHelper;
import tuum.tuum_test.persistence.mapper.TransactionMapper;
import tuum.tuum_test.persistence.model.Transaction;

@Service
public class TransactionService {

    @Autowired private TransactionMapper transactionMapper;

    @Autowired TransactionMapHelper transactionMapHelper;

    public List<Transaction> findTransactionsByAccountId(UUID accountId) {
        return transactionMapper.findTransactionsByAccountId(accountId);
    }

    public Transaction createTransaction(CreateTransactionDto createTransaction) {
        Transaction transaction = transactionMapHelper.mapToTransaction(createTransaction);
        transactionMapper.insertTransaction(transaction);

        return transaction;
    }
}
