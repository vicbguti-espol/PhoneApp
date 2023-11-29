package model.contacts;

import collections.CustomLinkedList;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import model.attributes.Attribute;
import model.attributes.phone.PhoneNumber;
import model.comparator.ComparatorUtil;

public abstract class Contact implements Serializable {
    public final List<Attribute> attributes = new CustomLinkedList<>();
    protected String uid;   
    
    public Contact(PhoneNumber phone) {
        initAttributesList(phone);
        initUID(phone);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }
    
    /**
     * Deprecated method
     * @param cmp
     * @param object
     * @return 
     */
    public List<Attribute> findAttributes(Comparator<Attribute> cmp, Attribute object) {
        List<Attribute> found = new CustomLinkedList<>();
        for ( Attribute attribute: attributes ) {
            if ( cmp.compare(attribute, object) == 0 ) {
                found.add(attribute);
            }
        }
        return found;
    }
    
    public List<Attribute> findByAttribute(Attribute attribute){
        CustomLinkedList<Attribute> customAttributes =
                new CustomLinkedList(attributes);
        return customAttributes.findAll(
                ComparatorUtil.cmpByAttribute, attribute);
    }
    
    public String getUID(){
        return uid;
    }
    
    private void initAttributesList(PhoneNumber phone){
        attributes.add(phone);   
    }
    
    private void initUID(PhoneNumber phone){
        uid = phone.getPhoneNumber();
    }
}
