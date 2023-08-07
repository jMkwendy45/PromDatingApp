package africa.semicolon.promiscuous.dto.request;

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
    private List<Recipients> recipients;
    private    List<String>copiedEmails;
    private String TextContents;
    private String subject;
    private String mailContents;

}
