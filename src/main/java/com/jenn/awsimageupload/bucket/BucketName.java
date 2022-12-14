package com.jenn.awsimageupload.bucket;

public enum BucketName {

    PROFILE_IMAGE("amigoscode-image-upload-jenn");

    private final String bucketName;

    BucketName(String bucketName){
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
