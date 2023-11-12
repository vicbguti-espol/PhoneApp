package model.attributes.names;

public class CompanyName extends Name {
    private static final long serialVersionUID = 5839214870383950486L;
    String companyName;

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
