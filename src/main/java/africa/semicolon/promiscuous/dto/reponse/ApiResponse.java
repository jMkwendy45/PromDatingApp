package africa.semicolon.promiscuous.dto.reponse;

import lombok.Builder;

@Builder
public class ApiResponse <T>{
    private T data;
}
