package com.mycompany;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
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
    protected TextField imageSourceTextField;
    protected Button btnImageAdding;
    
    
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
    
    private void initImageChooser(){
        initImageSourceText();
        initBtnImageDialog();
        initPagination();
        initFileDialog();
        addPagination();
    }
        
    private void initImageSourceText(){
        initImageTextField();
        imageSourceTextField.setEditable(false);
        imageSourceTextField.setFocusTraversable(false);
    }
    
    private void initBtnImageDialog(){
        initBtnImageAdding();
        btnImageAdding.setOnAction(e -> openImagesDialog());
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
        
    private void initFileDialog(){
        fileDialog = new FileChooser();
        fileDialog.getExtensionFilters()
                .add(new FileChooser.
                        ExtensionFilter("Imagen", 
                                "*.jpg", 
                                "*.png", 
                                "*.bmp", 
                                "*.gif"));
    }
    
    private void openImagesDialog(){
        fileDialog.setTitle("Abrir imagenes");
        imageList = fileDialog.showOpenMultipleDialog(App.stage);
        if(imageList != null) {
            pagination.setPageCount(imageList.size());
            imageSourceTextField.setText(imageList.get(0).getParent());
        }
    }
    
    void initialize(){
        initImageChooser();
        typeInitialization();
    }
    
    boolean isPrepared(){
        return imageList != null && 
                isTypePrepared();
    }
    
    
    abstract void initImageTextField();
    abstract void initBtnImageAdding();
    abstract void typeInitialization();
    abstract void addPagination();
    abstract void loadPhone(); 
    abstract void loadContact();
    abstract void loadLocation();
    abstract void addTypeAttributes();
    abstract boolean isTypePrepared();
    abstract void loadTypeData();
}
