package model.attributes;

import model.enums.SocialMediaType;

public class SocialMedia extends Attribute {
    private String User;
    private SocialMediaType SocialMedia; 
    
    public SocialMedia() {}

    public SocialMedia(String User, SocialMediaType SocialMedia) {
        this.User = User;
        this.SocialMedia = SocialMedia;
    }

    public String getUser() {
        return User;
    }

    public SocialMediaType getSocialMedia() {
        return SocialMedia;
    }
}
