package com.mycompany;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import model.contacts.Contact;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD
        //scene = new Scene(loadFXML("contactList"), 880, 870);
        scene = new Scene(loadFXML("editPresetAtribute"));
=======
        scene = new Scene(loadFXML("contactList"), 880, 870);
        //scene = new Scene(loadFXML("primary"));
>>>>>>> 2566038b85a0e54c1345947c768409895a17ef3c
        this.stage = stage;

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        this.stage.setX(bounds.getMinX());
        this.stage.setY(bounds.getMinY());
        this.stage.setWidth(bounds.getWidth());
        this.stage.setHeight(bounds.getHeight());
        
        this.stage.setScene(scene);
        this.stage.show();
    }

    public static void setRoot(String fxml){
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException ex) {
            System.err.println("Failed to load fxml: " + fxml);
            ex.printStackTrace();
        }
    }
    
    static void setRoot(String fxml, Controller controller) 
            throws IOException {
        scene.setRoot(loadFXML(fxml, controller));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    private static Parent loadFXML(String fxml, Controller controller)
            throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }
    
    public Stage getStage(){
        return stage;
    }
    
    public static void main(String[] args) {
        launch();
    }

}