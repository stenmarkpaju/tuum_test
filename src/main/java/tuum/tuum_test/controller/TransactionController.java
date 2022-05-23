package tuum.tuum_test.controller;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuum.tuum_test.dto.CreateTransactionDto;
import tuum.tuum_test.dto.GetTransactionDto;
import tuum.tuum_test.maphelper.TransactionMapHelper;
import tuum.tuum_test.persistence.model.Transaction;
import tuum.tuum_test.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;
    private TransactionMapHelper transactionMapHelper;

    @GetMapping("/{accountId}")
    public ResponseEntity<List<GetTransactionDto>> getTransactions(
            @PathVariable("accountId") String accountId) {
        UUID id = UUID.fromString(accountId);
        List<Transaction> transactions = transactionService.findTransactionsByAccountId(id);
        List<GetTransactionDto> transactionDtos =
                transactionMapHelper.mapToGetTransactionDtos(transactions);
        return ResponseEntity.ok(transactionDtos);
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<Transaction> addTransaction(
            @RequestBody CreateTransactionDto createTransactionDto) {
        Transaction transaction = transactionService.createTransaction(createTransactionDto);
        return ResponseEntity.ok(transaction);
    }
}
