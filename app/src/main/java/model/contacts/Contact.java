/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.contacts;

import java.util.ArrayList;
import model.attributes.Attribute;
import model.attributes.Generic;
import model.attributes.PhoneNumber;
import model.enums.ContactType;

/**
 *
 * @author vicbguti
 */
public class Contact {
    private ArrayList<Attribute> attributes;

    public Contact(ContactType contactType, PhoneNumber phoneNumber) {
        attributes = new ArrayList<>();
        attributes.add(new Generic(contactType));
        attributes.add(phoneNumber);
    }

    public Contact() {}
    
    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }
    
    
}
