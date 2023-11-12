/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.contacts.Contact;
import model.serialization.SerializationUtil;

/**
 *
 * @author vicbguti
 */
public class MobilePhone {
    private static final List<Contact> contactList = new ArrayList<>();
    private static final String path = "ser/contactList.ser";
    
    public static void addContact(Contact c) throws IOException{
        contactList.add(c);
        SerializationUtil.serialize(contactList, path);
    }
    
    public static List<Contact> getContactList() throws IOException, ClassNotFoundException{
        List<Contact> contactList2 = (List<Contact>) SerializationUtil.deserialize(path);
        return contactList2;
    }
}
