package africa.semicolon.promiscuous.repositories;

import africa.semicolon.promiscuous.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
