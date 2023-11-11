package model.attributes;

import model.enums.SourceType;

public class PhoneNumber extends Attribute implements Typable  {
    private String phoneNumber;
    private SourceType phoneType;

    public PhoneNumber(String phoneNumber, SourceType phoneType) {
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneNumber(){

    }
}
