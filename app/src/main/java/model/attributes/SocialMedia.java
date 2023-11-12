package model.attributes;

import model.enums.SocialMediaType;

public class SocialMedia {
    private String User;
    private SocialMediaType SocialMedia; 

    public SocialMedia(String User, SocialMediaType SocialMedia) {
        this.User = User;
        this.SocialMedia = SocialMedia;
    }
}
