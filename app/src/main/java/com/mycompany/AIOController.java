package com.mycompany;

import javafx.scene.layout.Pane;

public abstract class AIOController extends Controller {
    Pane rootPane;
    
    public Pane getRootPane(){
        return rootPane;
    }
    
    protected abstract void buildRootPane();
    

    
    
}
