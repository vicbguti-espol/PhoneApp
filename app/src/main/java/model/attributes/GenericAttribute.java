package model.attributes;

public class GenericAttribute<T> extends Attribute {
    private T value; 
    private String description;
    
    public GenericAttribute() {}
    
    public GenericAttribute(String attributeName, T value) {
        this.attributeName = attributeName;
        this.description = attributeName;
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

    public void setValue(T value) {
        this.value = value;
    }

    public void setDescription(String Descripcion) {
        this.description = Descripcion;
    }

    public String getDescription() {
        return description;
    }
    
    public T getValue() {
        return value;
    }
    
}
