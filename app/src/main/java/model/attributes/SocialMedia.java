/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.attributes;

import model.enums.SocialMediaType;


/**
 *
 * @author naomi
 */

public class SocialMedia {
     private String User;// se usa o no?
    private SocialMediaType SocialMedia; 

    public SocialMedia(String User, SocialMediaType SocialMedia) {
        this.User = User;
        this.SocialMedia = SocialMedia;
    }
}
