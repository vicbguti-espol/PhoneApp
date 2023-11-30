/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import collections.CustomLinkedList;
import com.mycompany.customizables.ImagePagination;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.attributes.Attribute;
import model.attributes.ContactImage;
import model.contacts.Contact;
/**
 * FXML Controller class
 *
 * @author arauj
 */
public class AddImagesController  extends DataEntryController implements Initializable {


    @FXML
    private Button btnReturn;
    @FXML
    private Button btnAddImages;
    @FXML
    private VBox content;
    
    protected List<ContactImage> images;
    private Contact contact;
    private ImagePaginationFileChooser imagePaginationFileChooser;

    public AddImagesController(Contact contact) {
        this.contact = contact;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialize();
    }
    
    private void addImages() {
        if (isPrepared()){
            loadInfo();
            addContactImages();
            try {
                super.returnContactPage(contact);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            sucessDialog();
        } else{
            noDataAlert();
        }
    }

    private void configureButtons() {
        btnReturn.setOnAction(e -> {
            try {
                super.returnContactPage(contact);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnAddImages.setOnAction(e -> addImages());
    }
    
    private class ImagePaginationFileChooser {
        private TextField imageSourceTextField;
        private Button imageAddButton;
        private FileChooser fileChooser;
        private ImagePagination imagePagination;
        private VBox container;
        private List<File> files; 
        
        void buildImagePaginationFileChooser(){
            buildSourceTextField();
            buildImageAddBtn();
            buildFileChooser(); 
            
            buildContainer();
        }
        
        Pane getContainer(){
            return container;
        }
        
        List<File> getFiles(){
            return files;
        }
        
        boolean isFilled(){
            return !imagePagination.getImageList().isEmpty();
        }
        
        private void buildContainer(){
            Label imagesLabel = new Label("Imágenes");
            HBox sourceHBox = new HBox(imageSourceTextField, 
                    imageAddButton);
            imagePagination = new ImagePagination();
            container = new VBox(
                    imagesLabel,
                    sourceHBox, 
                    imagePagination.getContainer());
        }
        

        
        private void buildSourceTextField(){
            imageSourceTextField = new TextField("Buscar imágenes");
            imageSourceTextField.setEditable(false);
            imageSourceTextField.setFocusTraversable(false);
        }
        
        private void buildImageAddBtn(){
            imageAddButton = new Button("...");
            imageAddButton.setOnAction(e -> openImagesDialog());
        }
        
        private void buildFileChooser(){
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                .add(new FileChooser.
                        ExtensionFilter("Imagen", 
                                "*.jpg", 
                                "*.png", 
                                "*.bmp", 
                                "*.gif"));
        }
        
        
        private void openImagesDialog(){
            fileChooser.setTitle("Abrir imagenes");
            List<Image> images = imagePagination.getImageList();
            images.clear();
            files = fileChooser.showOpenMultipleDialog(App.stage);
            files.forEach(e -> images.add(new Image(e.toURI().toString())));
            if(!images.isEmpty()) {
                imagePagination.initPagination();
            }
        }
        
    }
    
    void addContactImages(){
        List<Attribute> attributes = contact.attributes;
        attributes.addAll(images);
    }
    
    void loadInfo(){
        loadImages();
    }
    
    private void loadImages(){
        // handling image attribute
        images = new CustomLinkedList<>();
        for (File file: imagePaginationFileChooser.getFiles()){
            try{
                //get Url and open stream
                String urlString = file.toURI().toString();
                String[] splittedPath = urlString.split("/");
                String imageSRC = splittedPath[splittedPath.length - 1];
                InputStream inputStream = new URL(urlString).openStream();

                ContactImage newImage = new ContactImage();
                String source = "images/" + contact.getUID() + "/";
                Files.createDirectories(Paths.get(source));
                String targetPath = source + imageSRC;
                File target = new File(targetPath);

                //copy bytes from the stream to the target file
                Files.copy(inputStream, 
                        target.toPath(), 
                        StandardCopyOption.REPLACE_EXISTING);
                newImage.setPath(targetPath);
                images.add(newImage);
            } catch (Exception x){
                System.err.println("Failed to save Image");
                x.printStackTrace();
            }   
        }     
    }
    
    void initialize(){
        imagePaginationFileChooser = new ImagePaginationFileChooser();
        imagePaginationFileChooser.buildImagePaginationFileChooser();
        content.getChildren().add(imagePaginationFileChooser.getContainer());
        configureButtons();
    }
    
    boolean isPrepared(){
        return imagePaginationFileChooser.isFilled();
    }
    
}
