package model.attributes.Location;

import model.attributes.Location.Location;
import model.enums.SourceType;

public class PersonLocation extends Location {
    private SourceType locationType;

    public PersonLocation(SourceType locationType, String details, String mapsURL) {
        super(details, mapsURL);
        this.locationType = locationType;
    }
    
    
}
