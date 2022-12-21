package com.jenn.awsimageupload.profile;

import com.amazonaws.services.migrationhubstrategyrecommendations.model.Strategy;
import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "userprofile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UUID userProfileId;
    private String username;
    private String userProfileImageLink; // S3 key


//    public UserProfile(UUID userProfileId, String username, String userProfileImageLink) {
//        this.userProfileId = userProfileId;
//        this.username = username;
//        this.userProfileImageLink = userProfileImageLink;
//    }

    public UUID getUserProfileId() {
        return userProfileId;
    }

    public String getUsername() {
        return username;
    }

    public Optional<String> getUserProfileImageLink() {
        return Optional.ofNullable(userProfileImageLink);
    }

    public void setUserProfileImageLink(String userProfileImageLink) {
        this.userProfileImageLink = userProfileImageLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        //changed to Objects.equals
        return Objects.equals(userProfileId,that.userProfileId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(userProfileImageLink,that.userProfileImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProfileId, username, userProfileImageLink);
    }
}
