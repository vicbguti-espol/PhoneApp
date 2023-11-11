package model.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.attributes.Attribute;
import model.contacts.Contact;
import model.serialization.SerializationUtil;

public class UnitTesting {
    public static void main(String[] args) {
        try {
            List<Contact> contactList = (ArrayList<Contact>) 
                    SerializationUtil.
                            deserialize(MobilePhone.contactListPath);
            for (Contact contact: contactList){
                System.out.println(contact);
                for(Attribute at: contact.attributes){
                    System.out.println(at);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.err.println("Failed to get the list");
            ex.printStackTrace();
        }
    }
}
