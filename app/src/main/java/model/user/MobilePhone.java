package model.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.contacts.Contact;
import model.serialization.SerializationUtil;

public class MobilePhone {
    private static List<Contact> contactList;
    private static final String contactListPath = "ser/contactList.ser";
    
    public static List<Contact> getContactList(){
        if (contactList == null){
            initContactList();
        }
        return contactList;
    }
    
    public static void addContact(Contact c) {
        try {
            if (contactList == null) initContactList();
            contactList.add(c);
            SerializationUtil.serialize(contactList, contactListPath);
        } catch (IOException ex) {
            System.err.println("Failed to add a contact");
            ex.printStackTrace();
        }
    }
    
    public static void removeContact(Contact c){
        try {
            if (contactList == null) initContactList();
            contactList.remove(c);
            SerializationUtil.serialize(contactList, contactListPath);
        } catch (IOException ex) {
            System.out.println("Failed to remove the contact");
            ex.printStackTrace();
        }
    }
    
    private static void initContactList(){
        try {
            contactList =
                    (ArrayList<Contact>) SerializationUtil.
                            deserialize(contactListPath);
        } catch (Exception e){
            e.printStackTrace();
            contactList = new ArrayList<>();
        }
    }
}
