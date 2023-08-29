package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.UploadMediaResponse;
import africa.semicolon.promiscuous.dto.request.MediaReactionRequest;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    UploadMediaResponse uploadProfilePicture(MultipartFile file);
    UploadMediaResponse uploadMedia(MultipartFile file);
//    String reactToMedia(MediaReactionRequest mediaReactionRequest);

}
