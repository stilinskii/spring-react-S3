package com.jenn.awsimageupload.datasource;

import com.jenn.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILE = new ArrayList<>();

    static{
        USER_PROFILE.add(new UserProfile(UUID.randomUUID(),"jenn",null));
        USER_PROFILE.add(new UserProfile(UUID.randomUUID(),"jenny",null));
    }

    public List<UserProfile> getUserProfile(){
        return USER_PROFILE;
    }
}
