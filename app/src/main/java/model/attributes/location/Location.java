package model.attributes.location;

import java.util.ArrayList;
import model.attributes.Attribute;
import model.attributes.Typable;

public abstract class Location extends Attribute implements Typable{
    private String details;
    private String mapsURL;
    private ArrayList<Location> locations;
    public Location() {}

    public Location(String details, String mapsURL) {
        this.details = details;
        this.mapsURL = mapsURL;
        this.locations= new ArrayList<Location>();
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
    public void agregarMasL(Location location){
        locations.add(location);
    }
}
