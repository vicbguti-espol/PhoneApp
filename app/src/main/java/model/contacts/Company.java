package model.contacts;

import model.attributes.phone.PhoneNumber;

public class Company extends Contact {

    public Company(PhoneNumber phone) {
        super(phone);
        uid = "C" + super.uid;
    }
}
