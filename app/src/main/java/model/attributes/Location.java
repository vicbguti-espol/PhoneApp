package model.attributes;

public class Location extends Attribute {
    String description;
    String mapsURL;

    public Location(String description, String mapsURL) {
        this.description = description;
        this.mapsURL = mapsURL;
    }
    
    
}
