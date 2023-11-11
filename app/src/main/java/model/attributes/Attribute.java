/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.attributes;

import java.io.Serializable;

/**
 *
 * @author vicbguti
 */
public class Attribute implements Serializable {
    public String getAttributeName(){
        return this.getClass().getSimpleName();
    }
    
}
