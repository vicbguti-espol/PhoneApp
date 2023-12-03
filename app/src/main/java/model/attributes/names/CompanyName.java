package model.attributes.names;

public class CompanyName extends Name {
    String companyName;
    
    public CompanyName(){}

    public CompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    @Override
    public String toString() {
        return companyName;
    }
}
