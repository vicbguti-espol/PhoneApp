package model.contacts;

import java.util.List;
import model.attributes.Attribute;
import model.attributes.names.CompanyName;
import model.attributes.phone.PhoneNumber;
import model.comparator.ComparatorUtil;

public class Company extends Contact {

    public Company(PhoneNumber phone) {
        super(phone);
        uid = "C" + super.uid;
    }
    
    @Override
    public String toString(){
        List<Attribute> names = findAttributes(ComparatorUtil.cmpByAttribute, new CompanyName(""));
        CompanyName name = (CompanyName) names.get(0);
        return name.toString();
    }
}
