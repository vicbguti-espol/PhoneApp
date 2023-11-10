/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.contacts;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.image.Image;
import model.attributes.Attribute;
import model.attributes.Generic;
import model.attributes.PhoneNumbers;
import model.enums.ContactType;

/**
 *
 * @author vicbguti
 */
public class Contact {
    private ContactType contactType;
    private LinkedList<Attribute> attributes;

    public Contact(ContactType contactType, PhoneNumbers phoneNumber) {
        attributes = new LinkedList<>();
        this.contactType = contactType;
        attributes.add(new Generic(contactType));
        attributes.add(phoneNumber);
    }

    public Contact() {}
    
    public LinkedList<Attribute> getAttributes(){
        return attributes;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }
    
//    public Attribute getAttribute(Attribute attribute){
//        // TO-DO
//    }
    
//    public ArrayList<Attribute> findAll(
//            Comparator<Attribute> cmp, 
//            Attribute attribute
//            ){
//        // TO DO
//        // attributes.findAll(cmp, attribute);
//    } 
    
//    public static void main(String[] args) {
//        
//        // TO DO
//        
////        Comparator<Attribute> cmpByClass = new Comparator<>(){
////            @Override
////            public int compare(Attribute t, Attribute t1) {
////                if (t.getClass().getSimpleName().equals(t1.getClass().getSimpleName())) return 0;
////                else return 20;
////            }
////            
////        };
//
//    }
    
}
