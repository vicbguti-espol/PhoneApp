package model.user;

import collections.CustomLinkedList;
import collections.CustomList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import model.contacts.Contact;

public class MobilePhone {
    private static CustomList<Contact> contactList;
    private static CustomList<Contact>  favoritos;
    private static final String contactListPath = "ser/contactList.ser";
    private static final String contactListFavortitosPath = "ser/contactListfavortios.ser";
    
    private MobilePhone(){}
    
    public static CustomList<Contact> getContactList(){
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
            contactList = new CustomLinkedList<>();
            serialize();
        } else if (contactList == null){
            deserialize();
        }
    }
    
    private static void deserialize(){
        try {
            contactList = (CustomLinkedList<Contact>) SerializationUtil.
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
    
    
    public static CustomList<Contact> getContactListFavorito(){
        initContactListFavorito();
        return favoritos;
    }
    
    public static void addContactFavorito(Contact c) {
        initContactListFavorito();
        favoritos.add(c);
        serializeFavorito();
    }
    
    public static void removeContactFavorito(Contact c){
        initContactListFavorito();
        favoritos.remove(c);
        serializeFavorito();
    }
    
    private static void initContactListFavorito() {
        File f = new File(contactListFavortitosPath);
        if(!f.isFile()) {
            try {
                Files.createDirectories(Paths.get("ser"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        deserializeFavorito();
        
        if (favoritos == null){
            favoritos = new CustomLinkedList<>();
            serializeFavorito();
        }
    }
    
    private static void deserializeFavorito(){
        try {
            favoritos = (CustomLinkedList<Contact>) SerializationUtil.
                    deserialize(contactListFavortitosPath);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void serializeFavorito() {
        try {
            SerializationUtil.serialize(favoritos,contactListFavortitosPath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
