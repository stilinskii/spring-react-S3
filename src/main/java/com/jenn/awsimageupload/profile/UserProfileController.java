package com.jenn.awsimageupload.profile;

import com.amazonaws.services.elasticfilesystem.model.IncorrectFileSystemLifeCycleStateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/user-profile")

public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getUserProfiles(){
        return userProfileService.getUserProfiles();
    }

    @PostMapping(
            path = "{userProfileId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId,
                                       @RequestParam("file")MultipartFile file) throws IOException {
        userProfileService.uploadUserProfileImage(userProfileId, file);
        System.out.println(file.getContentType());

    }

//    @GetMapping("{userProfileId}/image/download")
//    public byte[] downloadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId){
//        return userProfileService.downloadUserProfileImage(userProfileId);
//    }

    @GetMapping("{userProfileId}/image/download")
    public String downloadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId){
        return userProfileService.getUserProfileImgUrl(userProfileId);
    }

    @PostMapping
    public String addNewUserProfile(){
        log.info("post request access");
        return "ok";
    }


}
