package ma.enset.digitalbanking_backend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@DiscriminatorValue("CA")
@NoArgsConstructor
public class CurrentAccount extends BankAccount {
    private double overDraft;
}
