module com.mycompany {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany to javafx.fxml;
    exports com.mycompany;
    
    opens model.attributes.phone;
    opens model.attributes.location;
    opens model.attributes.reminders;
    opens model.attributes.names;
    opens model.attributes.company;
    opens model.attributes;
    
}
