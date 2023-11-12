package model.attributes;

import model.enums.SourceType;

public class Location extends Attribute implements Typable{
    private static final long serialVersionUID = 3996574998611670292L;
    private SourceType locationType;
    private String details;
    private String mapsURL;

    public Location(SourceType locationType, String details, String mapsURL) {
        this.locationType = locationType;
        this.details = details;
        this.mapsURL = mapsURL;
    }

    @Override
    public String toString() {
        return "Location{" + "locationType=" + locationType + ", details=" + details + '}';
    }
    
    
}
