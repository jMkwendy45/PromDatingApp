package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {

     String upload(MultipartFile file);
}
