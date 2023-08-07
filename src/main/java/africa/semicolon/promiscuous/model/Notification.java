package africa.semicolon.promiscuous.model;

import jakarta.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;

    private Long sender;
    @ManyToOne
    private User user;
}
