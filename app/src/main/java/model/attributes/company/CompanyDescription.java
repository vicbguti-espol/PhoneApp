package model.attributes.company;

import java.util.Objects;
import model.attributes.Attribute;

public class CompanyDescription extends Attribute {
   String description;

    public CompanyDescription(String description) {
        this.description = description;
    }

    public CompanyDescription() {}

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompanyDescription other = (CompanyDescription) obj;
        return Objects.equals(this.description, other.description);
    }
    
    
   
}
