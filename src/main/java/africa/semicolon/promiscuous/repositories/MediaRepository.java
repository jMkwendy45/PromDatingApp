package africa.semicolon.promiscuous.repositories;

import africa.semicolon.promiscuous.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media,Long> {
}
