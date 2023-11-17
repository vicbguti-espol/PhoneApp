package model.attributes;

public class GenericAttribute extends Attribute {
    private String value; 
    private String Descripcion;
    
<<<<<<< HEAD
    public GenericAttribute(String attributeName, String value) {
=======
    public GenericAttribute() {}
    
    public GenericAttribute(String attributeName, T value) {
>>>>>>> 2566038b85a0e54c1345947c768409895a17ef3c
        this.attributeName = attributeName;
        this.value = value;
    }
    
    public GenericAttribute(String value){
        this.attributeName = value.getClass().getSimpleName();
        this.value = value;
    }
    
    @Override
    public String getAttributeName(){
        return attributeName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    public String getValue() {
        return value;
    }
    
}
