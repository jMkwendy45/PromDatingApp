package africa.semicolon.promiscuous.model;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String houseNumber;
    private String streets;
    private  String state;
    private  String country;
}
