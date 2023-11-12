package com.mycompany;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.attributes.Attribute;
import model.attributes.Image;
import model.attributes.location.Location;
import model.attributes.phone.PhoneNumber;
import model.contacts.Contact;
import model.user.MobilePhone;


public abstract class AddContactTypeController extends DataEntryController {
    
    protected FileChooser fileDialog;
    protected List<File> imageList;
    protected Pagination pagination;
    
    
    protected PhoneNumber phone;
    protected Location location;
    protected List<Image> images;
    protected Contact contact;
    
    private void saveContact(){
        MobilePhone.addContact(contact);
    }
    
    void addContact(){
        if (isPrepared()){
            loadInfo();
            addAttributes();
            saveContact();
            returnHomePage();
            sucessDialog();
        } else{
            noDataAlert();
        }
    }
    
    void addAttributes(){
        List<Attribute> attributes = contact.attributes;
        attributes.add(location);
        attributes.addAll(images);
        addTypeAttributes();
    }
    
    void addAttribute(Attribute attribute){
        List<Attribute> attributes = contact.attributes;
        attributes.add(attribute);
    }
    
    void loadInfo(){
        loadPhone();
        loadContact();
        loadLocation();
        loadImages();
        loadTypeData();
    }
    
    private void loadImages(){
    // handling image attribute
    images = new ArrayList<>();
    for (File file: imageList){
        try{
            //get Url and open stream
            String urlString = file.toURI().toString();
            String[] splittedPath = urlString.split("/");
            String imageSRC = splittedPath[splittedPath.length - 1];
            InputStream inputStream = new URL(urlString).openStream();
            
            Image newImage = new Image();
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
    
    private void initFileDialog(){
        fileDialog = new FileChooser();
        fileDialog.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Imagen", "*.jpg", "*.png", "*.bmp", "*.gif"));
    }
    
    private void initPagination(){
        pagination = new Pagination();
        pagination.setPageFactory(index -> {
        if (imageList != null && index < imageList.size()) {

            String url = imageList.get(index).toURI().toString();

            ImageView imageView = new ImageView(url);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(400);
            imageView.setFitWidth(600);

            return imageView;

        } else {
            return new Label("No hay imágenes seleccionadas");
        }
        });
    }
    
    private void initImageChooser(){
        initPagination();
        initImageSourceText();
        initFileDialog();
    }
    
    void initialize(){
        initImageChooser();
        addPagination();
    }
    
    boolean isPrepared(){
        return imageList != null && 
                isTypePrepared();
    }
    
    
    abstract void initImageSourceText();
    abstract void openImages(ActionEvent event);
    abstract void addPagination();
    abstract void loadPhone(); 
    abstract void loadContact();
    abstract void loadLocation();
    abstract void addTypeAttributes();
    abstract boolean isTypePrepared();
    abstract void loadTypeData();
}
