package ma.enset.digitalbanking_backend.repositories;

import ma.enset.digitalbanking_backend.entities.AccountOperation;
import ma.enset.digitalbanking_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

}
