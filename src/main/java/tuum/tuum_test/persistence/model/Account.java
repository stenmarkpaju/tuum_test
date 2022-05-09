package tuum.tuum_test.persistence.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private UUID accountId;
    private String customerId;
    private Double availableFunds;
    private Currency currency;
}
