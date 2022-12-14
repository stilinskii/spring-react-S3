package com.jenn.awsimageupload.profile;

import com.jenn.awsimageupload.bucket.BucketName;
import com.jenn.awsimageupload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.jenn.awsimageupload.bucket.BucketName.PROFILE_IMAGE;
import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles(){
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        //1. check if image is not empty
        isFileEmpty(file);

        //2. If file is an image
        isImage(file);

        //3. whether the user exists in our database
        UserProfile user = getUserProfileOrThrow(userProfileId);

        //4. Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);
        //5. Store the image in S3 and update database with S3 image link
        String path = String.format("%s/%s", PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        String filename = String.format("%s-%s", UUID.randomUUID(),file.getOriginalFilename());
        try {
            fileStore.save(path, filename, Optional.of(metadata),file.getInputStream());
            //final로 선언되어있지 않기때문에 set사용 가능
            user.setUserProfileImageLink(filename);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        //        userProfileDataAccessService
//                .getUserProfiles()
//                .stream()
//                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
        return userProfileDataAccessService.getUserById(userProfileId);
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String,String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length",String.valueOf(file.getSize()));
        return metadata;
    }

    private void isImage(MultipartFile file) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType(), IMAGE_TIFF.getMimeType()).contains(file.getContentType())){
            throw new IllegalStateException("File must be an image [" + file.getContentType() +"]");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()){
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }


    public String getUserProfileImgUrl(UUID userProfileId){
        UserProfile user = getUserProfileOrThrow(userProfileId);
        String path = String.format("%s/%s",
                PROFILE_IMAGE.getBucketName(),
                user.getUserProfileId());
        return user.getUserProfileImageLink()
                .map(fileName -> fileStore.getImgUrl(path, fileName))
                .orElseThrow(() -> new IllegalStateException("file does not exists"));
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile user = getUserProfileOrThrow(userProfileId);
        String path = String.format("%s/%s",
                PROFILE_IMAGE.getBucketName(),
                user.getUserProfileId());

        //..?뭐지 그냥 String인데 Optional이라서 가능한가봄
        return user.getUserProfileImageLink()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);

    }
}
