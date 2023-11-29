package model.contacts;

import collections.CustomLinkedList;
import model.attributes.Attribute;
import model.attributes.names.PersonName;
import model.attributes.phone.PhoneNumber;
import model.comparator.ComparatorUtil;

public class Person extends Contact {
    
    public Person(PhoneNumber phoneNumber) {
        super(phoneNumber);
        uid = "P" + super.uid;
    }
    
    @Override
    public String toString(){
        CustomLinkedList<Attribute> names = (CustomLinkedList<Attribute>) findAttributes(ComparatorUtil.cmpByAttribute, new PersonName("",""));
        PersonName name = (PersonName) names.getFirst();
        return name.toString();
    }

    @Override
    public String getType() {
        return "Persona";
    }
}
