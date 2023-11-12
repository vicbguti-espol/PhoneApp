package model.attributes.company;

import model.attributes.Attribute;

public class CompanyWebPage extends Attribute {
    String webPage;

    public CompanyWebPage(String webPage) {
        this.webPage = webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getWebPage() {
        return webPage;
    }
   
   
}
