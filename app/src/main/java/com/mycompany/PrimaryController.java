package com.mycompany;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.attributes.Attribute;
import model.attributes.ContactImage;
import model.comparator.ComparatorUtil;
import model.contacts.Contact;
import model.user.MobilePhone;


public class PrimaryController implements Initializable {
    @FXML
    private Button btnAddContact;
    @FXML
    private Button btnAddAtribute;
    @FXML
    private VBox primaryVBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAddContact.setOnAction(e -> {
            App.setRoot("chooseContactType");
        });
        btnAddAtribute.setOnAction(e -> {
            App.setRoot("chooseAttributeType");
        });
        
//        
//        Contact contact = MobilePhone.getContactList().get(0);
//        List<Attribute> images = contact.findAttributes(
//                ComparatorUtil.cmpByAttribute, 
//                new ContactImage());
//        ContactImage cimg = (ContactImage) images.get(0);
//        System.out.println(cimg.getPath());
//
//        Image img = null;
//        try {
//            img = new Image(new FileInputStream(cimg.getPath()));
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
//        primaryVBox.getChildren().add(new ImageView(img));
    }
}
