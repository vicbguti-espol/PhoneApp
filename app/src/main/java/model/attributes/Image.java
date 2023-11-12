package model.attributes;

public class Image extends Attribute {
    private static final long serialVersionUID = 6834438291049916110L;
    String path;

    public Image(){
        this.path = "";
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}