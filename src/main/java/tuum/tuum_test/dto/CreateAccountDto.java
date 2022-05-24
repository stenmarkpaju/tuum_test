package tuum.tuum_test.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateAccountDto {
    private String customerId;
    private String Country;
    private List<String> currency;
}
