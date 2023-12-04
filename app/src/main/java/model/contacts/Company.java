package model.contacts;

import collections.CustomList;
import model.attributes.Attribute;
import model.attributes.names.CompanyName;
import model.attributes.phone.PhoneNumber;
import model.util.comparator.ComparatorUtil;

public class Company extends Contact {

    public Company(PhoneNumber phone) {
        super(phone);
        uid = "C" + super.uid;
    }
    
    @Override
    public String toString(){
        CustomList<Attribute> names = (CustomList<Attribute>) 
                findAttributes(ComparatorUtil.cmpByAttribute, new CompanyName(""));
        CompanyName name = (CompanyName) names.getFirst();
        return name.toString();
    }

    @Override
    public String getType() {
        return "Empresa";
    }
}
