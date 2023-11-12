package com.mycompany;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        
        
        scene = new Scene(loadFXML("primary"));
        this.stage = stage;
        this.stage.setScene(scene);
        this.stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
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