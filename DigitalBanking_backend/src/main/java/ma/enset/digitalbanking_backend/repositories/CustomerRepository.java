package ma.enset.digitalbanking_backend.repositories;

import ma.enset.digitalbanking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
