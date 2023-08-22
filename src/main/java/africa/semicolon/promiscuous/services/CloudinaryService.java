package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.config.AppConfig;
import africa.semicolon.promiscuous.dto.reponse.ApiResponse;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@Service
@AllArgsConstructor
public class CloudinaryService implements CloudService {
    private final AppConfig appConfig;

    @Override
    public String upload(MultipartFile file) {

        Cloudinary cloudinary = new Cloudinary();
        Uploader uploader = cloudinary.uploader();
        try {
            Map<?, ?> response = uploader.upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id", "Promiscuous/Users/profile_images/" + file.getName(),
                    "api_key", appConfig.getCloudKey(),
                    "api_secret", appConfig.getCloudSecret(),
                    "cloud_name", appConfig.getCloudName(),
                    "secure", true
            ));
            return response.get("url").toString();
        } catch (IOException exception) {
            throw new RuntimeException("File upload failed");

        }
    }
}
