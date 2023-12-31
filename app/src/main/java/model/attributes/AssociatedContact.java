package model.attributes;

import model.contacts.Contact;

public class AssociatedContact extends Attribute {
    private Contact contact;
    private String relation;
    
    public AssociatedContact() {}

    public AssociatedContact(Contact contact, String relation) {
        this.contact = contact;
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "AssociatedContact{" + "contact=" + contact + ", relation=" + relation + '}';
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
