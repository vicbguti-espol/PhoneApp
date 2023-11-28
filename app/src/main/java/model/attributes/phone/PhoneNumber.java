package model.attributes.phone;

import java.util.ArrayList;
import java.util.Objects;
import model.attributes.Attribute;
import model.attributes.Typable;

public abstract class PhoneNumber extends Attribute implements Typable  {
    private String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneNumber(){

    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PhoneNumber other = (PhoneNumber) obj;
        return Objects.equals(this.phoneNumber, other.phoneNumber);
    }
    
    
}
