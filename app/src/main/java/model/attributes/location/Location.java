package model.attributes.location;

import model.attributes.Attribute;
import model.attributes.Typable;

public abstract class Location extends Attribute implements Typable{
    private String details;
    private String mapsURL;

    public Location(String details, String mapsURL) {
        this.details = details;
        this.mapsURL = mapsURL;
    }
}
