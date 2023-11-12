package model.attributes;

import java.io.Serializable;

public abstract class Attribute implements Serializable {
    String attributeName;
    
    public Attribute(){
        attributeName = this.getClass().getSimpleName();
    }
    
    public String getAttributeName(){
        return attributeName;
    }
    
    @Override
    public String toString(){
        return "Attribute Name: " + attributeName;
    }
    
    
}
