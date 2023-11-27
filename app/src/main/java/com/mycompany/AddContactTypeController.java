package com.mycompany;

import collections.CustomIterator;
import collections.CustomLinkedList;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    ImagePagination imagePagination;
    
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
    
    private class ImagePagination {
        private TextField imageSourceTextField;
        private Button imageAddButton;
        private FileChooser fileChooser;
        private List<File> imageList;
        private CustomIterator<File> circularIterator;
        
        private ImageViewChanger imageViewChanger;
        private Button rightButton;
        private Button leftButton;
        VBox container;
        
        void buildImagePagination(){
            buildSourceTextField();
            buildImageAddBtn();
            buildFileChooser();
            buildImageList();
            buildImageViewChanger();
            buildButtons();
            buildContainer();
        }
        
        VBox getContainer(){
            return container;
        }
        
        void buildImageList(){
            imageList = new CustomLinkedList<>();
        }
        
        void buildImageViewChanger(){
            ImageView imageView = new ImageView();
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(400);
            imageView.setFitWidth(600);
            imageViewChanger = new ImageViewChanger(imageView);
        }
        
        boolean isFilled(){
            return imageList.size() > 0;
        }
        
        Iterator<File> filesIterator(){
            return new FilesIterator(imageList);
        }
        
        private class FilesIterator implements Iterator<File>{
            Iterator<File> it;
            
            FilesIterator(List<File> files){
                it = files.iterator();
            }
            
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public File next() {
                return it.next();
            }
            
        }
        
        private void buildContainer(){
            Label imagesLabel = new Label("Imágenes");
            HBox sourceHBox = new HBox(imageSourceTextField, 
                    imageAddButton);
            HBox buttonsHBox = new HBox(leftButton, rightButton);
            VBox imagePaginationVBox = new VBox(buttonsHBox,
                    imageViewChanger.getImageView());
            
            
            container = new VBox(
                    imagesLabel,
                    sourceHBox, 
                    imagePaginationVBox);
        }
        
        private void buildButtons(){
            leftButton = new Button("◄");
            rightButton = new Button("►");
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
        
        private class ImageViewChanger {
            ImageView imageView;
            
            ImageViewChanger(ImageView imageView){
                this.imageView = imageView;
            }
            
            void changeImage(File file){
                String url = file.toURI().toString();
                new Thread(()->{
                    Platform.runLater(()->{
                        imageView.setImage(new Image(url));
                    });
                }).start();
            }
            
            ImageView getImageView(){
                return imageView;
            }
        }
        
        private abstract class ButtonEventHandler implements EventHandler{
            CustomIterator<File> circularIterator;
            ImageViewChanger imageViewChanger;
            
            ButtonEventHandler(CustomIterator<File> circularIterator, 
                    ImageViewChanger imageViewChanger){
                this.circularIterator = circularIterator;
                this.imageViewChanger = imageViewChanger;
            }
            
            void setImageViewChanger(ImageViewChanger imageViewChanger){
                this.imageViewChanger = imageViewChanger;
            }
                    
            @Override
            public void handle(Event t) {
                File file = getFile();
                imageViewChanger.changeImage(file);
            }
            
            abstract File getFile();
        
        }
        
        private class RightButtonEventHandler extends ButtonEventHandler{

            public RightButtonEventHandler(CustomIterator<File> circularIterator,
                    ImageViewChanger imageViewChanger) {
                super(circularIterator, imageViewChanger);
            }

            @Override
            File getFile() {
                return circularIterator.next();
            }
        
        }
        
        private class LeftButtonEventHandler extends ButtonEventHandler{

            public LeftButtonEventHandler(CustomIterator<File> circularIterator,
                    ImageViewChanger imageViewChanger) {
                super(circularIterator, imageViewChanger);
            }

            @Override
            File getFile() {
                return circularIterator.previous();
            }
        
        }
        
        void buildCircularIterator(){
            CustomLinkedList<File> circularImageList = 
                new CustomLinkedList<>();
            imageList.forEach(e -> circularImageList.add(e));
            circularIterator = circularImageList.circularIterator();
        }
        
        private void openImagesDialog(){
            fileChooser.setTitle("Abrir imagenes");
            imageList = fileChooser.showOpenMultipleDialog(App.stage);
            if(imageList != null) {
                buildCircularIterator();
                imageViewChanger.changeImage(circularIterator.next());
                rightButton.setOnAction(new RightButtonEventHandler(
                        circularIterator, imageViewChanger));
                leftButton.setOnAction(new LeftButtonEventHandler(
                        circularIterator, imageViewChanger));
                imageSourceTextField.setText(
                        imageList.get(0).getParent());
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
        images = new ArrayList<>();
        Iterator<File> filesIterator = imagePagination.filesIterator();
        while (filesIterator.hasNext()){
            try{
                //get Url and open stream
                String urlString = filesIterator.next().toURI().toString();
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
        imagePagination = new ImagePagination();
        imagePagination.buildImagePagination();
        centerVBox.getChildren().add(imagePagination.getContainer());
        typeInitialization();
    }
    
    boolean isPrepared(){
        return imagePagination.isFilled() && isTypePrepared();
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
