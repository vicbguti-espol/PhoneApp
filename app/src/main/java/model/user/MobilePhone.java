package model.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.contacts.Contact;
import model.serialization.SerializationUtil;

public class MobilePhone {
    private static final List<Contact> contactList = new ArrayList<>();
    static final String contactListPath = "ser/contactList.ser";
    
    public static void addContact(Contact c) throws IOException{
        contactList.add(c);
        SerializationUtil.serialize(contactList, contactListPath);
    }
}
