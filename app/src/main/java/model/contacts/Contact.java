package model.contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import model.attributes.Attribute;
import model.attributes.phone.PhoneNumber;

public abstract class Contact implements Serializable {
    public LinkedList<Attribute> attributes;
    protected String uid;   
    
    public Contact(PhoneNumber phone) {
        initAttributesList(phone);
        initUID(phone);
    }

    public LinkedList<Attribute> getAttributes() {
        return attributes;
    }
    
    public List<Attribute> findAttributes(Comparator<Attribute> cmp, Attribute object) {
        List<Attribute> found = new ArrayList<>();
        for ( Attribute attribute: attributes ) {
            if ( cmp.compare(attribute, object) == 0 ) {
                found.add(attribute);
            }
        }
        return found;
    }
    
    public String getUID(){
        return uid;
    }
    
    @Override
    public String toString(){
        return uid;
    }
    
    private void initAttributesList(PhoneNumber phone){
        attributes = new LinkedList<>();
        attributes.add(phone);   
    }
    
    private void initUID(PhoneNumber phone){
        uid = phone.getPhoneNumber();
    }
}
