/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import model.attributes.Attribute;
import model.attributes.ContactImage;
import model.attributes.names.CompanyName;
import model.attributes.names.Name;
import model.attributes.names.PersonName;
import model.comparator.ComparatorUtil;
import model.contacts.Company;
import model.contacts.Contact;
import model.contacts.Person;

/**
 * FXML Controller class
 *
 * @author arauj
 */
public class ContactImagesController extends Controller implements Initializable {

    @FXML
    private Button btnReturn;
    @FXML
    private Label lblTitle;
    @FXML
    private Pagination imgPagination;
    
    private Contact contact;
    private List<ContactImage> images;
    
    
    public ContactImagesController(){}

    public ContactImagesController(Contact contact) {
        this.contact = contact;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getImageList();
        lblTitle.setText("Fotos de " + getNameContact());
        System.out.println(images.size());
        imgPagination.setPageCount(images.size());
        
        imgPagination.setPageFactory(new Callback<Integer, Node>(){
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });
        
        btnReturn.setOnAction(r -> {
            try {
                returnContactPage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }    
    
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }
    
    private Name getNameContact(){
        Name name = null;
        if (contact instanceof Company){
            name = new CompanyName("random");
        } else if (contact instanceof Person){
            name = new PersonName("random","random");
        }
        List<Attribute> names = contact.findAttributes(ComparatorUtil.cmpByAttribute, name);
        return (Name) names.get(0);
    }
    
    private void getImageList(){
        images = new ArrayList<>();
        List<Attribute> imagesAtt = contact.findAttributes(ComparatorUtil.cmpByAttribute, new ContactImage());
        for (Attribute image : imagesAtt){
            if(image instanceof ContactImage){
                images.add((ContactImage) image);
            }
        }
    }
    
    private ImageView createPage(int index){
        ImageView imageView = new ImageView();
        ContactImage image = images.get(index);
        Image img = null;
        try {
            img = new Image(new FileInputStream(image.getPath()));
        } catch (Exception e) {
            System.out.println("No se encontró la ruta de la imagen");
            //img = new Image(new FileInputStream("images/default.png"));
        }
        imageView.setImage(img);
        imageView.setFitHeight(500);
        imageView.setFitWidth(500);
        return imageView;
    }
}
