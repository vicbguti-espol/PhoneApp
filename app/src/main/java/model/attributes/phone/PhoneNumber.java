package model.attributes.phone;

import java.util.ArrayList;
import model.attributes.Attribute;
import model.attributes.Typable;

public abstract class PhoneNumber extends Attribute implements Typable  {
    private String phoneNumber;
    private ArrayList<PhoneNumber> phonenumbers;

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.phonenumbers= new ArrayList<PhoneNumber>();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneNumber(){

    }
    public void agregarMasN(PhoneNumber phoneNumber){
        phonenumbers.add(phoneNumber);
    }
}
