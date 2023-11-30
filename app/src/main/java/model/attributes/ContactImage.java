package model.attributes;

public class ContactImage extends Attribute {
    String path;

    public ContactImage(){
        this.path = "";
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
    
    @Override
    public String toString(){
        return path;
    }

}