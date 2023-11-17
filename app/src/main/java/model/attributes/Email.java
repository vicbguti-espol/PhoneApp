/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.attributes;

import model.enums.SourceType;

/**
 *
 * @author arauj
 */
public class Email extends Attribute {
    private SourceType emailType;
    private String email;

    public Email(SourceType emailType, String email) {
        this.emailType = emailType;
        this.email = email;
    }

    public Email() {}

    public SourceType getEmailType() {
        return emailType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmailType(SourceType emailType) {
        this.emailType = emailType;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
