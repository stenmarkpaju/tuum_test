package tuum.tuum_test.persistence.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private UUID accountId;
    private UUID transactionId;
    private Double amount;
    private Currency currency;
    private TransactionDirection transactionDirection;
    private String description;
}
