package africa.semicolon.promiscuous.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class ActivateAccountResponse {
    private String message;
    private GetUserResponse user;

}
