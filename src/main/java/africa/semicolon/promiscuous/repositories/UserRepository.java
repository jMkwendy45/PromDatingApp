package africa.semicolon.promiscuous.repositories;

import africa.semicolon.promiscuous.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {
    Optional<User> readByEmail(String email);
}
