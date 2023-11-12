/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.attributes;

import model.contacts.Contact;

/**
 *
 * @author arauj
 */
public class AssociatedContact extends Attribute {
    private Contact contact;
    private String relation;

    public AssociatedContact(Contact contact, String relation) {
        this.contact = contact;
        this.relation = relation;
    }

    public Contact getContact() {
        return contact;
    }

    public String getRelation() {
        return relation;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    
    
    
}
