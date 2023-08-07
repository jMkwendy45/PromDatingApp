package africa.semicolon.promiscuous.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.List;
@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private List<Reaction>reactions;
    @ManyToOne
    private  User user;
    private  Integer reactionCount = BigInteger.ZERO.intValue();
}
