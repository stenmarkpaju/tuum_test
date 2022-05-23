package tuum.tuum_test.dto;

import lombok.Builder;
import lombok.Data;
import tuum.tuum_test.persistence.model.Currency;
import tuum.tuum_test.persistence.model.TransactionDirection;

@Builder
@Data
public class CreateTransactionDto {
    private String accountId;
    private Double amount;
    private Currency currency;
    private TransactionDirection transactionDirection;
    private String description;
}
