package africa.semicolon.promiscuous.dto.reponse;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {
        private Long id;
        private String email;
        private String fullName;
        private String phoneNumber;
        private String address;
}
