package tuum.tuum_test.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import tuum.tuum_test.persistence.model.Currency;

@Builder
@Data
public class CreateAccountDto {
    private String customerId;
    private String Country;
    private List<Currency> currency;
}
