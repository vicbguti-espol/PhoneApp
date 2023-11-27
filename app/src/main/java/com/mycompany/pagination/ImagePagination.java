package com.mycompany.pagination;

import collections.CustomIterator;
import collections.CustomLinkedList;
import java.io.File;
import java.util.List;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ImagePagination {
    private final List<File> filesList = new CustomLinkedList<>();
    private CustomIterator<File> circularIterator;
    private ImageViewChanger imageViewChanger;
    private Button rightButton;
    private Button leftButton;
    private VBox vBox;

    public ImagePagination(){
        buildImagePagination();
    }

    public List<File> getFiles(){
        return filesList;
    }

    public Pane getContainer(){
        return vBox;
    }

    /**
     * Method used to start viewing something in the container
     */
    public void initPagination(){
        buildCircularIterator();
        imageViewChanger.changeImage(circularIterator.next());
        rightButton.setOnAction(new RightButtonEventHandler(
                circularIterator, imageViewChanger));
        leftButton.setOnAction(new LeftButtonEventHandler(
                circularIterator, imageViewChanger));
    }



    private void buildImagePagination(){
        buildImageViewChanger();
        buildButtons();
        buildContainer();
    }



    private void buildContainer(){
        HBox buttonsHBox = new HBox(leftButton, rightButton);
        vBox = new VBox(buttonsHBox,
                imageViewChanger.getImageView());
    }

    private void buildImageViewChanger(){
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(400);
        imageView.setFitWidth(600);
        imageViewChanger = new ImageViewChanger(imageView);
    }

    private void buildButtons(){
        leftButton = new Button("◄");
        rightButton = new Button("►");
    }

    private void buildCircularIterator(){
        CustomLinkedList<File> circularImageList = 
            new CustomLinkedList<>();
        filesList.forEach(e -> circularImageList.add(e));
        circularIterator = circularImageList.circularIterator();
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
}
