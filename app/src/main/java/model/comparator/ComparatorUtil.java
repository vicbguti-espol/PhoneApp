package model.comparator;

import java.util.Comparator;
import model.attributes.Attribute;

public class ComparatorUtil {
    public static Comparator<Attribute> cmpByAttribute = (t1, t2) -> 
        {
            if (t1.getAttributeName().equals(t2.getAttributeName())) 
                return 0;
            else return 100;
        };;
}
