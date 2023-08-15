package africa.semicolon.promiscuous.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Recipients {
    private String name;
    @NonNull
    private String email;
}
