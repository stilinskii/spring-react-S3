package com.jenn.awsimageupload.datasource;

import com.amazonaws.services.dynamodbv2.xspec.NULL;
import com.jenn.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILE = new ArrayList<>();

    static{
        USER_PROFILE.add(new UserProfile(UUID.fromString("735e904c-f71e-4f05-815d-b7501e964c2f"),"jenn",null));
        USER_PROFILE.add(new UserProfile(UUID.fromString("05e74b35-5073-4af8-9a34-34972914dbad"),"jenny",null));
    }

    public List<UserProfile> getUserProfile(){
        return USER_PROFILE;
    }

    public UserProfile getUserById(UUID userProfileId){
       return USER_PROFILE
               .stream()
               .filter((userProfile -> userProfile.getUserProfileId().equals(userProfileId)))
               .findFirst()
               .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
    }
}
