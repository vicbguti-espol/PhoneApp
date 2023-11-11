package com.mycompany;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.attributes.company.CompanyDescription;
import model.attributes.Location.CompanyLocation;
import model.attributes.company.CompanyWebPage;
import model.attributes.names.CompanyName;
import model.attributes.phone.CompanyPhone;
import model.contacts.Company;


public class AddCompanyController extends AddContactTypeController
        implements Initializable {
    
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtWebPage;
    @FXML
    private TextField txtLocationDescription;
    @FXML
    private TextField txtLocationURL;
    @FXML
    private TextField txtImageSource;
    @FXML
    private VBox boxImages;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnAdd;
    
    private CompanyDescription description;
    private CompanyWebPage webpage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        btnReturn.setOnAction(e -> super.returnHomePage());
        btnAdd.setOnAction(e -> super.addContact());
        
    }

    @Override
    protected void loadPhone() {
        phone = new CompanyPhone(txtPhoneNumber.getText());
    }

    @Override
    void initImageSourceText(){
        txtImageSource.setEditable(false);
        txtImageSource.setFocusTraversable(false);
    }

    @Override
    @FXML
    void openImages(ActionEvent event) {
        fileDialog.setTitle("Abrir imagenes");
        imageList = fileDialog.showOpenMultipleDialog(App.stage);
        if(imageList != null) {
            pagination.setPageCount(imageList.size());
            txtImageSource.setText(imageList.get(0).getParent());
        }
    }

    @Override
    void addPagination(){
        boxImages.getChildren().add(pagination);
    }


    @Override
    void loadContact() {
        contact = new Company(phone);
        contact.attributes.add(new CompanyName(txtName.getText()));
    }

    @Override
    void loadLocation() {
        location = new CompanyLocation(txtLocationDescription.getText(),
        txtLocationURL.getText());
    }

    @Override
    boolean isTypePrepared() {
        return !txtName.getText().equals("")
                && !txtDescription.getText().equals("")
                && !txtPhoneNumber.getText().equals("")
                && !txtWebPage.getText().equals("")
                && !txtLocationDescription.getText().equals("")
                && !txtLocationURL.getText().equals("");
    } 
    
    @Override
    void addTypeAttributes(){
        addAttribute(description);
        addAttribute(webpage);
    }

    @Override
    void loadTypeData() {
        description = new CompanyDescription(txtDescription.getText());
        webpage = new CompanyWebPage(txtWebPage.getText());
    }
}
