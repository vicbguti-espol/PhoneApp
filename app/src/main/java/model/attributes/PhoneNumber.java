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
public class PhoneNumber extends Attribute {
    private String phoneNumber;
    private SourceType phoneType;

    public PhoneNumber(String phoneNumber, SourceType phoneType) {
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
    }
    
    
}
