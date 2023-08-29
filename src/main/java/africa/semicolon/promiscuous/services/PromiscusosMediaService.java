package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.UploadMediaResponse;
import africa.semicolon.promiscuous.dto.request.MediaReactionRequest;
import africa.semicolon.promiscuous.exception.PromiscuousException;
import africa.semicolon.promiscuous.model.Media;
import africa.semicolon.promiscuous.model.MediaReaction;
import africa.semicolon.promiscuous.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PromiscusosMediaService implements MediaService{

    private final CloudService cloudService;
    private final MediaRepository mediaRepository;

//    @Autowired
//    public PromiscusosMediaService (CloudService cloudService, MediaRepository mediaRepository){
//        this.cloudService = cloudService;
//        this.mediaRepository = mediaRepository;
//    }
    @Override
    public UploadMediaResponse uploadProfilePicture(MultipartFile file) {
        String url = cloudService.upload(file);
        UploadMediaResponse uploadMediaResponse = new UploadMediaResponse();
        uploadMediaResponse.setMessage(url);
        return uploadMediaResponse;
    }

    @Override
    public UploadMediaResponse uploadMedia(MultipartFile file) {
        cloudService.upload(file);
        UploadMediaResponse response = new UploadMediaResponse();
        response.setMessage("Profile picture updated");
        return response;
    }

//    @Override
//    public String reactToMedia(MediaReactionRequest mediaReactionRequest) {
//        Media media=mediaRepository.findById(mediaReactionRequest.getMediaId())
//                .orElseThrow(()->
//                        new PromiscuousException(MEDIA_NOT_FOUND.name()));
//        MediaReaction reaction = new MediaReaction();
//        reaction.setReaction(mediaReactionRequest.getReaction());
//        reaction.setUser(mediaReactionRequest.getUserId());
//        media.getReactions().add(reaction);
//        mediaRepository.save(media);
//        return SUCCESS.name();
//    }

}

