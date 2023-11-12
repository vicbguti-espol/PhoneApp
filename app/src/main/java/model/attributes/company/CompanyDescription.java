package model.attributes.company;

import model.attributes.Attribute;

public class CompanyDescription extends Attribute {
   String description;

    public CompanyDescription(String description) {
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
   
   
}
