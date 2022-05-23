package tuum.tuum_test.persistence.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private UUID accountId;
    private String customerId;
    private String country;
    @Builder.Default private List<Balance> balance = new ArrayList<>();
}
