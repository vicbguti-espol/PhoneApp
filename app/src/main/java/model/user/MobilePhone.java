package model.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import model.contacts.Contact;

public class MobilePhone {
    private static List<Contact> contactList;
    private static final String contactListPath = "ser/contactList.ser";
    
    public static List<Contact> getContactList(){
        initContactList();
        return contactList;
    }
    
    public static void addContact(Contact c) {
        initContactList();
        contactList.add(c);
        serialize();
    }
    
    public static void removeContact(Contact c){
        initContactList();
        contactList.remove(c);
        serialize();
    }
    
    public static void updateContactList(){
        initContactList();
        serialize();
    }
    
    private static void initContactList() {
        
        File f = new File(contactListPath);
        if(!f.isFile()) { 
            try {
                Files.createDirectories(Paths.get("ser"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            contactList = new LinkedList<>();
            serialize();
        } else if (contactList == null){
            deserialize();
        }
    }
    
    private static void deserialize(){
        try {
            contactList = (LinkedList<Contact>) SerializationUtil.
                    deserialize(contactListPath);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void serialize() {
        try {
            SerializationUtil.serialize(contactList,contactListPath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
