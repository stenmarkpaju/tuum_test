package tuum.tuum_test.persistence.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Balance {
    private Double availableFunds;
    @Enumerated(value = EnumType.STRING)
    private Currency currency;
}
