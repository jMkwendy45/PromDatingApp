package africa.semicolon.promiscuous.dto.reponse;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FindAllMessageResponse {
    private List<String> message;
}
