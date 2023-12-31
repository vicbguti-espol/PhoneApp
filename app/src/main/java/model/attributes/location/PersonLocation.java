package model.attributes.location;

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

    public void setLocationType(SourceType locationType) {
        this.locationType = locationType;
    }
    
}
