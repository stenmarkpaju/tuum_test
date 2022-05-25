package tuum.tuum_test.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import tuum.tuum_test.persistence.model.Currency;
import tuum.tuum_test.persistence.model.TransactionDirection;

@Builder
@Data
public class CreateTransactionDto {
    private UUID accountId;
    private Double amount;
    private Currency currency;
    private TransactionDirection transactionDirection;
    private String description;
}
