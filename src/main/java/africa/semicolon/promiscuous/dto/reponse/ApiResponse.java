package africa.semicolon.promiscuous.dto.reponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ApiResponse <T>{
    private T data;
}
