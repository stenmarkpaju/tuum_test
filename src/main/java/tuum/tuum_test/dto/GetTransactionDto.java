package tuum.tuum_test.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import tuum.tuum_test.persistence.model.Currency;
import tuum.tuum_test.persistence.model.TransactionDirection;

@Data
@Builder
public class GetTransactionDto {
    private UUID transactionId;
    private Double amount;
    private Currency currency;
    private TransactionDirection transactionDirection;
    private String description;
}
