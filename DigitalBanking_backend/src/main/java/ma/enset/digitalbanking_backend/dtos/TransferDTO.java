package ma.enset.digitalbanking_backend.dtos;

import lombok.Data;
import ma.enset.digitalbanking_backend.entities.BankAccount;
@Data
public class TransferDTO {
    private String bankAccountSourceId;
    private String bankAccountDestinationId;
    private double amount;
}
