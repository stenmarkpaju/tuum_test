package tuum.tuum_test.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuum.tuum_test.dto.CreateTransactionDto;
import tuum.tuum_test.dto.CreatedTransactionDto;
import tuum.tuum_test.dto.GetTransactionDto;
import tuum.tuum_test.exception.*;
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
            @PathVariable("accountId") String accountId)
            throws AccountNotFoundException, TransactionNotFoundException {
        UUID id = UUID.fromString(accountId);
        List<Transaction> transactions =
                Optional.of(transactionService.findTransactionsByAccountId(id))
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                "Account not found for the given id: "
                                                        + accountId));
        List<GetTransactionDto> transactionDtos =
                transactionMapHelper.mapToGetTransactionDtos(transactions);
        return ResponseEntity.ok(transactionDtos);
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<CreatedTransactionDto> createTransaction(
            @RequestBody CreateTransactionDto createTransactionDto)
            throws InsufficientFundsException, AccountNotFoundException,
                    InvalidMonetaryAmountException, InvalidDescriptionException {
        CreatedTransactionDto transaction =
                transactionService.createTransaction(createTransactionDto);
        return ResponseEntity.ok(transaction);
    }
}
