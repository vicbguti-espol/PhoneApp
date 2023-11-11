package model.attributes.phone;

import model.attributes.Attribute;
import model.attributes.Typable;

public abstract class PhoneNumber extends Attribute implements Typable  {
    private String phoneNumber;
    

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneNumber(){

    }
}
