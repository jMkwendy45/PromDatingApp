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

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append(houseNumber)
                .append(",")
                .append(streets)
                .append(",")
                .append(state)
                .append(",")
                .append(country);

        return builder.toString();
    }
}
