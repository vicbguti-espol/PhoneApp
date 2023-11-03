module com.mycompany {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany to javafx.fxml;
    exports com.mycompany;
}
