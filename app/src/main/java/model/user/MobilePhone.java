package model.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.contacts.Contact;
import model.serialization.SerializationUtil;

public class MobilePhone {
    private static List<Contact> contactList;
    static final String contactListPath = "ser/contactList.ser";
    
    public static void addContact(Contact c) throws IOException{
        if (contactList == null) initContactList();
        contactList.add(c);    
        SerializationUtil.serialize(contactList, contactListPath);
    }
    
    public static void initContactList(){
        try {
            contactList =
                    (ArrayList<Contact>) SerializationUtil.
                            deserialize(contactListPath);
        } catch (Exception e){
            e.printStackTrace();
            contactList = new ArrayList<>();
        }
    }
    
    public static List<Contact> getContactList(){
        if (contactList == null){
            initContactList();
        }
        return contactList;
    }
}
