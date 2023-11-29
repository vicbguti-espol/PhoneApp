package model.comparator;

import java.util.Comparator;
import model.attributes.Attribute;
import model.attributes.GenericAttribute;

public class ComparatorUtil {
    public static final Comparator<Attribute> cmpByAttribute = (t1, t2) -> 
        {
            if(t1 instanceof GenericAttribute && t2 instanceof GenericAttribute){
                return 0;
            }
            if (t1.getAttributeName().equals(t2.getAttributeName())) 
                return 0;
            else return 100;
        };;
}
