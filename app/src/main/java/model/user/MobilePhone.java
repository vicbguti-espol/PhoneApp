package model.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.contacts.Contact;
import model.serialization.SerializationUtil;

public class MobilePhone {
    private static List<Contact> contactList = new ArrayList<>();

    static final String contactListPath = "ser/contactList.ser";
    
    public static void addContact(Contact c) throws IOException{
        contactList.add(c);
        SerializationUtil.serialize(contactList, contactListPath);
    }
    
    public static List<Contact> getContactList(){
        if (contactList == null){
            try {
                contactList =
                        (ArrayList<Contact>) SerializationUtil.
                                deserialize(contactListPath);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
                contactList = new ArrayList<>();
            }
        }
        return contactList;
    }
}
