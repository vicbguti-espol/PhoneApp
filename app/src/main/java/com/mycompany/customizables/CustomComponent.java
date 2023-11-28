package com.mycompany.customizables;

import javafx.scene.layout.Pane;

public abstract class CustomComponent {
    protected Pane container;
    
    public void buildComponent(){
        buildSubComponents();
        buildContainer();
    }
    
    public Pane getContainer(){
        return container;
    }
    
    protected abstract void buildContainer();
    protected abstract void buildSubComponents();
}
