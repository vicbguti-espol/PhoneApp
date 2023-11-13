package model.attributes.location;

import model.attributes.location.Location;
import model.enums.SourceType;

public class PersonLocation extends Location {
    private SourceType locationType;

    public PersonLocation(){}
    
    public PersonLocation(SourceType locationType, String details, String mapsURL) {
        super(details, mapsURL);
        this.locationType = locationType;
    }

    public SourceType getLocationType() {
        return locationType;
    }
}
