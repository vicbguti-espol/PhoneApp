package model.attributes;

import model.enums.SourceType;

public class Location extends Attribute implements Typable{
    private SourceType locationType;
    private String details;
    private String mapsURL;

    public Location(SourceType locationType, String details, String mapsURL) {
        this.locationType = locationType;
        this.details = details;
        this.mapsURL = mapsURL;
    }
}
