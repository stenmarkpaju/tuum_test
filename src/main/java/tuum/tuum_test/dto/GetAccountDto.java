package tuum.tuum_test.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import tuum.tuum_test.persistence.model.Balance;

@Data
@Builder
public class GetAccountDto {
    private UUID accountId;
    private String customerId;
    List<Balance> balances;
}
