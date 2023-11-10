/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.attributes;

/**
 *
 * @author vicbguti
 */
public class Generic<T> extends Attribute {
    private String attributeName;
    private T value; 
    
    public Generic(String attributeName, T value) {
        this.attributeName = attributeName;
        this.value = value;
    }
    
    public Generic(T value){
        this.attributeName = value.getClass().getSimpleName();
        this.value = value;
    }
    
    @Override
    public String getAttributeName(){
        return attributeName;
    }
}
