package africa.semicolon.promiscuous.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private  String password;
    private LocalDate dateOfBirth;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;
    @Enumerated(EnumType.STRING)
    private  Gender gender;

    @Enumerated(EnumType.STRING)
    private  Role role;

    private boolean isActive;

    private LocalDateTime createdAt;


    @PrePersist
    public void setCreatedAt(){
        createdAt = LocalDateTime.now();
    }
}
