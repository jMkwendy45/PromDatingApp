package africa.semicolon.promiscuous.cloud;

import africa.semicolon.promiscuous.dto.reponse.ApiResponse;
import africa.semicolon.promiscuous.services.CloudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static africa.semicolon.promiscuous.utils.AppUtils.TEST_EMAIL_LOCATION;

public class CloudServiceTest {
    @Autowired

    private CloudService cloudService;



    @Test
    public void testUploadFiles(){
        Path path = Paths.get(TEST_EMAIL_LOCATION );
        try(InputStream inputStream = Files.newInputStream(path)){
            MultipartFile file = new MockMultipartFile("testImage",inputStream);
            ApiResponse<String>response = cloudService.upload()
        }catch (IOException exception){
            throw  new RuntimeException(":(");
        }

                )

    }


}
