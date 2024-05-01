package ma.enset.digitalbanking_backend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.digitalbanking_backend.enums.OperationType;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private Double amount;
    private OperationType operationType;
    private String description;
}
