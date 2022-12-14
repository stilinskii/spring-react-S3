package com.jenn.awsimageupload.profile;

import com.jenn.awsimageupload.datasource.FakeUserProfileDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileDataAccessService {

    //이렇게 하면 데이터 소스를 쉽게 바꿀 수 있따.
    //지금은 fake source이지만 다른 데이터로 바꾸기가 쉬워짐.
    private final FakeUserProfileDataStore fakeUserProfileDataStore;

    @Autowired
    public UserProfileDataAccessService(FakeUserProfileDataStore fakeUserProfileDataStore) {
        this.fakeUserProfileDataStore = fakeUserProfileDataStore;
    }

    List<UserProfile> getUserProfiles(){
        return fakeUserProfileDataStore.getUserProfile();
    }
}
