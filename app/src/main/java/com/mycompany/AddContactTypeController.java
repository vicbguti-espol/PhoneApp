package com.mycompany;

import collections.CustomLinkedList;
import view.ImagePagination;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
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
import model.attributes.location.Location;
import model.attributes.phone.PhoneNumber;
import model.contacts.Contact;
import model.user.MobilePhone;


public abstract class AddContactTypeController extends DataEntryController {
    protected VBox centerVBox;
    protected PhoneNumber phone;
    protected Location location;
    protected List<ContactImage> images;
    protected Contact contact;
    private ImagePaginationFileChooser imagePaginationFileChooser;
    
    private void saveContact() {
        MobilePhone.addContact(contact);
    }
    
    protected void setCenterVBox(VBox centerVBox){
        this.centerVBox = centerVBox;
    }
    
    void addContact() {
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
    
    void addAttributes(){
        Pagination p = new Pagination();
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
        buildCenterVBox();
        imagePaginationFileChooser = new ImagePaginationFileChooser();
        imagePaginationFileChooser.buildImagePaginationFileChooser();
        centerVBox.getChildren().add(imagePaginationFileChooser.getContainer());
        typeInitialization();
    }
    
    boolean isPrepared(){
        return imagePaginationFileChooser.isFilled() && isTypePrepared();
    }
    
    abstract void typeInitialization();
    abstract void loadPhone(); 
    abstract void loadContact();
    abstract void loadLocation();
    abstract void addTypeAttributes();
    abstract boolean isTypePrepared();
    abstract void loadTypeData();
    abstract void buildCenterVBox();
}
