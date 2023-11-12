package model.attributes;

import model.enums.SourceType;

public class PhoneNumber extends Attribute implements Typable  {
    private static final long serialVersionUID = 5760033430586424399L;
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

    @Override
    public String toString() {
        return "PhoneNumber{" + "phoneNumber=" + phoneNumber + ", phoneType=" + phoneType + '}';
    }
    
    
}
