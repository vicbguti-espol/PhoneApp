package model.attributes.phone;

import model.enums.SourceType;

public class PersonPhone extends PhoneNumber {
    private SourceType phoneType;

    public PersonPhone(String phoneNumber, SourceType phoneType) {
        super(phoneNumber);
        this.phoneType = phoneType;
    }

    public PersonPhone() {
    }   
}