/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
    @FXML
    private BorderPane root;
    @FXML
    private VBox centerVBox;
    
    //private ImageContactPagination imagePagination;
    
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
        centerVBox = new VBox();
        
        /*imagePagination = new ContactImagesController.ImageContactPagination();
        imagePagination.buildImageContactPagination();
        centerVBox.getChildren().add(imagePagination.getContainer());
        root.setCenter(centerVBox);*/
        
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
    
    /*private class ImageContactPagination {
        private ImagePagination imagePagination;
        private VBox container;
        
        void buildImageContactPagination(){
            fillList();
            buildContainer();
        }
        
        Pane getContainer(){
            return container;
        }
        
        ImagePagination getImagePagination(){
            return imagePagination;
        }
        
        boolean isFilled(){
            return !imagePagination.getFiles().isEmpty();
        }
        
        private void buildContainer(){
            Label imagesLabel = new Label("Imágenes");
            imagePagination = new ImagePagination();
            container = new VBox(
                    imagesLabel,
                    imagePagination.getContainer());
        }
        
        
        private void fillList(){
            List<File> files = imagePagination.getFiles();
            files.clear();
            images.forEach(action -> {
                System.out.println(action.getPath());
                files.add(Paths.get(action.getPath()).toFile());
                    });
            if(files != null) imagePagination.initPagination();
        }
        
    }*/
    
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
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(500);
        imageView.setFitWidth(500);
        return imageView;
    }
}
