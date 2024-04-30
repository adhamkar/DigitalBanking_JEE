package ma.enset.digitalbanking_backend.repositories;

import ma.enset.digitalbanking_backend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long>{
}
