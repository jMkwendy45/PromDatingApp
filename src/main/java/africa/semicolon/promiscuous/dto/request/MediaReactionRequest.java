package africa.semicolon.promiscuous.dto.request;

import africa.semicolon.promiscuous.enums.Reaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class MediaReactionRequest {
    private Reaction reaction;
    private Long mediaId;
    private Long userId;
}
