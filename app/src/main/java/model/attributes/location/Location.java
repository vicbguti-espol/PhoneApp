package model.attributes.location;

import model.attributes.Attribute;
import model.attributes.Typable;

public abstract class Location extends Attribute implements Typable{
    private String details;
    private String mapsURL;
    
    public Location() {}

    public Location(String details, String mapsURL) {
        this.details = details;
        this.mapsURL = mapsURL;
    }

    public String getDetails() {
        return details;
    }

    public String getMapsURL() {
        return mapsURL;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setMapsURL(String mapsURL) {
        this.mapsURL = mapsURL;
    }
}
