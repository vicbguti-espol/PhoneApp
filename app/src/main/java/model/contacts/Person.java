package model.contacts;

import java.util.List;
import model.attributes.Attribute;
import model.attributes.names.Name;
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
        List<Attribute> names = findAttributes(ComparatorUtil.cmpByAttribute, new PersonName("p","p"));
        PersonName name = (PersonName) names.get(0);
        return name.toString();
    }
}
