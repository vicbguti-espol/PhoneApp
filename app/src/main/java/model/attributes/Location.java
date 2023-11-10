/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.attributes;

import model.enums.SourceType;

/**
 *
 * @author vicbguti
 */
public class Location {
    private SourceType locationType;
    private String details;
    private String mapsURL;

    public Location(SourceType locationType, String details, String mapsURL) {
        this.locationType = locationType;
        this.details = details;
        this.mapsURL = mapsURL;
    }
    
    
    
}
