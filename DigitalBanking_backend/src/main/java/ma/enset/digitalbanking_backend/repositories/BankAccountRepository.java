package ma.enset.digitalbanking_backend.repositories;

import ma.enset.digitalbanking_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
