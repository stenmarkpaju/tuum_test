package tuum.tuum_test.maphelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import tuum.tuum_test.dto.CreateTransactionDto;
import tuum.tuum_test.dto.GetTransactionDto;
import tuum.tuum_test.persistence.model.Transaction;

@Component
public class TransactionMapHelper {
    public List<GetTransactionDto> mapToGetTransactionDtos(List<Transaction> transactions) {
        List<GetTransactionDto> dtos = new ArrayList<>();
        transactions.forEach(transaction -> dtos.add(mapToGetTransactionDto(transaction)));

        return dtos;
    }

    public GetTransactionDto mapToGetTransactionDto(Transaction transaction) {
        return GetTransactionDto.builder()
                .transactionId(transaction.getTransactionId())
                .transactionDirection(transaction.getTransactionDirection())
                .currency(transaction.getCurrency())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .build();
    }

    public Transaction mapToTransaction(CreateTransactionDto createTransactionDto) {
        return Transaction.builder()
                .accountId(UUID.fromString(createTransactionDto.getAccountId()))
                .transactionId(UUID.randomUUID())
                .amount(createTransactionDto.getAmount())
                .currency(createTransactionDto.getCurrency())
                .description(createTransactionDto.getDescription())
                .transactionDirection(createTransactionDto.getTransactionDirection())
                .build();
    }
}
