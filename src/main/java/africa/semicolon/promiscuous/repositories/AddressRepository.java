package africa.semicolon.promiscuous.repositories;

import africa.semicolon.promiscuous.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address,Long> {
}
