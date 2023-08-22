package africa.semicolon.promiscuous.dto.request;

import africa.semicolon.promiscuous.enums.Interest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
@Setter
@Getter
public class UpdateRequest {
    private Long id;
    private String dateOfBirth;
    private String lastName;
    private LocalDateTime localDateTime;
    private String firstName;
    private String password;
    private String gender;
    private Set<String> interest;
    private MultipartFile profileImage;
    private String phoneNumber;
    private String houseNumber;
    private String street;
    private String state;
    private String country;
    private Set<Interest> interests;

}
