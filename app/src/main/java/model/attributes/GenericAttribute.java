package model.attributes;

public class GenericAttribute<T> extends Attribute {
    private T value; 
    private String descripcion;
    
    public GenericAttribute() {}
    
    public GenericAttribute(String attributeName, T value) {
        this.attributeName = attributeName;
        this.descripcion = attributeName;
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

    public void setDescripcion(String Descripcion) {
        this.descripcion = Descripcion;
    }
    
    public T getValue() {
        return value;
    }
    
}
