package model.contacts;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;
import model.attributes.Attribute;
import model.attributes.PhoneNumber;

public abstract class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final AtomicLong atomicRefId = new AtomicLong();
    // transient field is not serialized
    private transient long refId;

    public LinkedList<Attribute> attributes;

    // default constructor will be called on base class even during deserialization
    public Contact() {
       refId = atomicRefId.incrementAndGet();
    }

    public Contact(PhoneNumber phone) {
        this();
        attributes = new LinkedList<>();
        attributes.add(phone);
    }
    
    public Attribute find(Comparator<Attribute> cmp, Attribute object) {
        for ( Attribute attribute: attributes ) {
            if ( cmp.compare(attribute, object) == 0 ) {
                return attribute;
            }
        }
        return null;
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

    public long getRefId() {
        return refId;
    }
    
}
