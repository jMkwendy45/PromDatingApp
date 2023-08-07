package africa.semicolon.promiscuous.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotificationRequest {
    private Sender sender;
    @JsonProperty("to")
    private List<Recipients> recipients;
    @JsonProperty("cc")
    private List<String>copiedEmails;
    private String TextContents;
    private String subject;
    @JsonProperty("htmlContent")
    private String mailContents;

}
