package model.attributes;

public class GenericAttribute<T> extends Attribute {
    private T value; 
    
    public GenericAttribute(String attributeName, T value) {
        this.attributeName = attributeName;
        this.value = value;
    }
    
    public GenericAttribute(T value){
        this.attributeName = value.getClass().getSimpleName();
        this.value = value;
    }
    
    @Override
    public String getAttributeName(){
        return attributeName;
    }
    
    public T getValue() {
        return value;
    }
    
}
