/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.util.comparator;

import java.util.Comparator;
import model.attributes.Attribute;
import model.attributes.names.CompanyName;

/**
 *
 * @author naomi
 */
public class ComparatorPorCompania implements Comparator<Attribute>{
    public int compare(Attribute attr1, Attribute attr2) {
        if (attr1 instanceof CompanyName && attr2 instanceof CompanyName) {
            CompanyName person1 = (CompanyName) attr1;
            CompanyName person2 = (CompanyName) attr2;

           return person1.getCompanyName().compareTo(person2.getCompanyName());  
        }

        // Manejar otros tipos de atributos aquí si es necesario
        return 0; // Cambia esto según tus necesidades
    }
}
