package com.jenn.awsimageupload.profile;

import com.jenn.awsimageupload.bucket.BucketName;
import com.jenn.awsimageupload.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.jenn.awsimageupload.bucket.BucketName.PROFILE_IMAGE;

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

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) throws IOException {
        //1. check if image is not empty
        //2. If file is an image
        if(isImage(file)){
        //3. whether the user exists in our database
            if(userExists(userProfileId)){
        //4. Grab some metadata from file if any
                Map<String, String> metaData = new HashMap<>();
        //5. Store the image in S3 and update database with S3 image link
                metaData.put(file.getOriginalFilename(),String.valueOf(file.getSize()));
                fileStore.save(PROFILE_IMAGE.getBucketName(), file.getOriginalFilename(), Optional.of(metaData),file.getInputStream());
            }
        }



    }

    private boolean userExists(UUID userProfileId) {
        return userProfileDataAccessService.getUserById(userProfileId) != null;
    }

    private boolean isImage(MultipartFile file) {
        return !Objects.isNull(file) && (file.getContentType()).contains("image/");
    }
}
