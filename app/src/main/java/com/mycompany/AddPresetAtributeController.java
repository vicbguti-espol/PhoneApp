package com.mycompany;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.attributes.AssociatedContact;
import model.attributes.Attribute;
import model.attributes.Email;
import model.attributes.SocialMedia;
import model.attributes.location.CompanyLocation;
import model.attributes.location.Location;
import model.attributes.location.PersonLocation;
import model.attributes.phone.CompanyPhone;
import model.attributes.phone.PersonPhone;
import model.attributes.phone.PhoneNumber;
import model.attributes.reminders.GenericReminder;
import model.contacts.Contact;
import model.enums.SocialMediaType;
import model.enums.SourceType;
import model.user.MobilePhone;

/**
 * FXML Controller class
 *
 * @author arauj
 */
public class AddPresetAtributeController extends DataEntryController implements Initializable {

    
    @FXML
    private Label mensaje;
    @FXML
    private ComboBox<String> combo;
    @FXML
    private String editar="";
    @FXML
    private Button btn_ReturnContact;
    @FXML
    private Button btn_Add;
    @FXML
    private BorderPane root;
    @FXML
    private HBox header;

    @FXML
    private VBox content;
    
    private Contact contact;
    private Attribute attribute;
    private String className;
    private char contactType;
    
    AddPresetAtributeController(Contact selectedContact, Attribute attribute, String className) {
        contact = selectedContact;
        this.attribute = attribute;
        this.className = className;
        contactType = contact.getUID().charAt(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mensaje.setText("Agregar " + className);
        btn_ReturnContact.setOnAction(e -> {
             try {
                 returnContactPage();
             } catch (IOException ex) {
                 ex.printStackTrace();
             }
        });
        

        ArrayList<Field> allFields = new ArrayList<>();
        Class<?> currentClass = attribute.getClass();
        int c = 0;
        if (className.equals("PersonLocation")
                || className.equals("PersonPhone")
                || className.equals("CompanyPhone")
                || className.equals("CompanyLocation")){
            c = 1;
        }

        for (int i = 0; i <= c; i++){
            Field[] fields = currentClass.getDeclaredFields(); //obtener atributos de clase
            for (Field f: fields){
                allFields.add(f);
            }
            currentClass = currentClass.getSuperclass();
        }
        content = new VBox();
        VBox labels = new VBox();
        VBox info = new VBox();
        HBox hbox = new HBox(labels,info);
        labels.setSpacing(30);
        labels.setAlignment(Pos.TOP_LEFT);
        info.setSpacing(30);
        info.setAlignment(Pos.TOP_LEFT);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        for (Field f: allFields){
            Label label = new Label(f.getName());
            labels.getChildren().add(label);
            
            if (f.getType().getSimpleName().equals("SourceType")) {
                ComboBox<String> comboBox = new ComboBox<>();
                comboBox.getItems().addAll(SourceType.PERSONAL.name(),SourceType.WORK.name());
                info.getChildren().add(comboBox);
            } else if (f.getType().getSimpleName().equals("SocialMediaType")){
                ComboBox<String> comboBox = new ComboBox();
                comboBox.getItems().addAll(SocialMediaType.INSTAGRAM.name(), SocialMediaType.X.name(),SocialMediaType.FACEBOOK.name());
                info.getChildren().add(comboBox);
            } else if (f.getType().getSimpleName().equals("Contact")){
                ComboBox<Contact> comboBox = new ComboBox();
                comboBox.getItems().addAll(MobilePhone.getContactList());
                info.getChildren().add(comboBox);
            }else {
                TextField textField = new TextField();
                info.getChildren().add(textField);
            }
        }
        content.getChildren().add(hbox);
        content.setSpacing(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));

        root.setCenter(content);
        
         btn_Add.setOnAction(e -> {
            agregables();
        });
        configureHeader(); 
    } 
    
    protected void configureHeader() {
        header.setSpacing(30);
        header.setAlignment(Pos.CENTER);
    }
    
    public void agregables(){
        if (attribute instanceof PhoneNumber){
            addNumero();
        }else if(attribute instanceof Location){
            addLocation();
        }else if(className.equals("Email")){
            addEmail();
        }else if(className.equals("SocialMedia")){
            addRedes();
        }else if(className.equals("AssociatedContact")){
            addAssociated();
        }
        super.sucessDialog();
        MobilePhone.updateContactList();
        try {
            super.returnContactPage(contact);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
     
    public void addNumero(){
        Attribute p1 = null;
        if (contactType == 'P'){
            HBox hbox= (HBox) content.getChildren().get(0);
            ComboBox comboBox = (ComboBox) hbox.getChildren().get(1);
            String combo = (String) comboBox.getSelectionModel().getSelectedItem();
            HBox hbox1= (HBox) content.getChildren().get(1);
            TextField t1=(TextField) hbox1.getChildren().get(1);
            if(combo.equals("PERSONAL")){
                p1= new PersonPhone(t1.getText(),SourceType.PERSONAL);
            }else{
                 p1= new PersonPhone(t1.getText(),SourceType.WORK);
            }
        } else if (contactType == 'C'){
            HBox hbox1= (HBox) content.getChildren().get(0);
            TextField t1=(TextField) hbox1.getChildren().get(1);
            p1= new CompanyPhone(t1.getText());
        }
        contact.getAttributes().add(p1);
    }
    
    public void addLocation(){
        Attribute p1 = null;
        if (contactType == 'P'){
            HBox hbox= (HBox) content.getChildren().get(0);
            ComboBox comboBox = (ComboBox) hbox.getChildren().get(1);
            String combo = (String) comboBox.getSelectionModel().getSelectedItem();
            
            HBox hbox1= (HBox) content.getChildren().get(1);
            HBox hbox2= (HBox) content.getChildren().get(2);
            TextField t1=(TextField) hbox1.getChildren().get(1);
            TextField t2=(TextField) hbox2.getChildren().get(1);

            if(combo.equals("PERSONAL")){
                p1= new PersonLocation(SourceType.PERSONAL,t1.getText(),t2.getText());
            }else{
                p1= new PersonLocation(SourceType.WORK,t1.getText(),t2.getText());
            }
        } else if (contactType == 'C'){
            HBox hbox1= (HBox) content.getChildren().get(0);
            HBox hbox2= (HBox) content.getChildren().get(1);

            TextField t1=(TextField) hbox1.getChildren().get(1);
            TextField t2=(TextField) hbox2.getChildren().get(1);
            p1= new CompanyLocation(t1.getText(),t2.getText());
        }
        contact.getAttributes().add(p1);
    }
    
    public void addRedes(){
        Attribute p1 = null;
        HBox hbox= (HBox) content.getChildren().get(0);
        HBox hbox1= (HBox) content.getChildren().get(1);
        TextField t1 = (TextField) hbox.getChildren().get(1);           
        ComboBox comboBox = (ComboBox) hbox1.getChildren().get(1);
        String combo = (String) comboBox.getSelectionModel().getSelectedItem();
        if(combo.equals("INSTAGRAM")){
            p1= new SocialMedia(t1.getText(),SocialMediaType.INSTAGRAM);
            contact.getAttributes().add(p1); 
        }else if(combo.equals("FACEBOOK")){
            p1= new SocialMedia(t1.getText(),SocialMediaType.FACEBOOK);
            contact.getAttributes().add(p1); 
        }else{
            p1= new SocialMedia(t1.getText(),SocialMediaType.X);
            contact.getAttributes().add(p1); 
        }  
    }
    
    public void addEmail(){
        Attribute p1 = null;
        HBox hbox= (HBox) content.getChildren().get(0);
        HBox hbox1= (HBox) content.getChildren().get(1);
        ComboBox comboBox = (ComboBox) hbox.getChildren().get(1);
        TextField t1=(TextField) hbox1.getChildren().get(1);
        String combo = (String) comboBox.getSelectionModel().getSelectedItem();
        if(combo.equals("PERSONAL")){  
            p1= new Email(SourceType.PERSONAL,t1.getText());
            contact.getAttributes().add(p1); 
        }else{
            p1= new Email(SourceType.WORK,t1.getText());
            contact.getAttributes().add(p1); 
        }  
    }
    
    public void addAssociated(){
        Attribute p1 = null;
        HBox hbox= (HBox) content.getChildren().get(0);
        HBox hbox1= (HBox) content.getChildren().get(1);
        ComboBox comboBox = (ComboBox) hbox.getChildren().get(1);
        TextField t1=(TextField) hbox1.getChildren().get(1);
        Contact combo = (Contact) comboBox.getSelectionModel().getSelectedItem();
        p1= new AssociatedContact(combo,t1.getText());
        contact.getAttributes().add(p1);
    }
    
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }
}