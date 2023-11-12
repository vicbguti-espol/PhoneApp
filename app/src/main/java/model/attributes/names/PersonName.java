package model.attributes.names;

import model.attributes.names.Name;

public class PersonName extends Name {
    private static final long serialVersionUID = 7121038299013450624L;
    String firstName;
    String lastName;

    public PersonName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "PersonName{" + "firstName=" + firstName + ", lastName=" + lastName + '}';
    }
    
    
    
}
