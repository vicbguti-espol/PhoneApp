package com.mycompany;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.attributes.Attribute;
import model.attributes.reminders.Reminder;
import model.contacts.Contact;
import model.user.MobilePhone;
import java.util.Comparator;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.attributes.AssociatedContact;
import model.attributes.Email;
import model.attributes.SocialMedia;
import model.attributes.company.CompanyDescription;
import model.attributes.company.CompanyWebPage;
import model.attributes.location.CompanyLocation;
import model.attributes.location.Location;
import model.attributes.location.PersonLocation;
import model.attributes.names.CompanyName;
import model.attributes.names.PersonName;
import model.attributes.phone.CompanyPhone;
import model.attributes.phone.PersonPhone;
import model.attributes.phone.PhoneNumber;
import model.attributes.reminders.GenericReminder;
import model.comparator.ComparatorUtil;
import model.enums.SocialMediaType;
import model.enums.SourceType;

public class EditPresetAtributeController extends DataEntryController implements Initializable {

    private List<Contact> contactList;
    private Comparator<Attribute> cmp;
    private Location location;
    private PersonName personaname;
    private Reminder reminder;
    private AssociatedContact associatedContact;
    private Email email;
    private CompanyWebPage companyWebPage ;
    private CompanyDescription companyDescription;
    private CompanyName companyName;
    private PhoneNumber phoneNumber;
    ComparatorUtil cmd;
    
    @FXML
    private TextField box_dato;
    @FXML
    private ComboBox<String> tiposA;
    private Boolean confirma=false;
    private List<Contact> Tcontactos;
    private List<Contact> modificar;
    private List<Attribute>  Alista;
    private String editar="";
    @FXML
    private Label mensaje;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnEdit;
    @FXML
    private BorderPane root;
    
    //contacto pasado
    private Contact contact;
    private Attribute attribute;

    private String delcombo;
    private String POC;
    private Boolean  why= false;

    private char contactType;

    @FXML
    private VBox content;
    
    
    EditPresetAtributeController(Contact selectedContact, Attribute att) {
        contact = selectedContact;
        attribute = att;

        editar=attribute.getAttributeName();
        contactType = contact.getUID().charAt(0);

    }
    
    
    public void initialize(URL url, ResourceBundle rb) {
       mensaje.setText("Editar " + editar); 
        btnReturn.setOnAction(e -> {
             try {
                 returnContactPage();
             } catch (IOException ex) {
                 ex.printStackTrace();
             }
        });
       
       ArrayList<Field> allFields = new ArrayList<>();
       Class<?> currentClass = attribute.getClass();
       int c = 0;
        if (editar.equals("PersonLocation")
                || editar.equals("PersonPhone")
                || editar.equals("CompanyPhone")
                || editar.equals("CompanyLocation")){
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
        for (Field f: allFields){
            HBox hbox = new HBox();
            Label label = new Label(f.getName());
            hbox.getChildren().add(label);
            if (f.getType().getSimpleName().equals("SourceType")) {
                ComboBox<String> comboBox = new ComboBox<>();
                comboBox.getItems().addAll(SourceType.PERSONAL.name(),SourceType.WORK.name());
                hbox.getChildren().add(comboBox);
            } else if (f.getType().getSimpleName().equals("SocialMediaType")){
                ComboBox<String> comboBox = new ComboBox();
                comboBox.getItems().addAll(SocialMediaType.INSTAGRAM.name(), SocialMediaType.X.name(),SocialMediaType.FACEBOOK.name());
                hbox.getChildren().add(comboBox);
            } else {
                TextField textField = new TextField();
                hbox.getChildren().add(textField);
            }
            content.getChildren().add(hbox);
        }
        //class model.contacts.Person
        root.setCenter(content);
        
        if(POC.equals("class model.contacts.Person")){
             System.out.println("funcionaaaaaaa");
             why=true;
             System.out.println(why);
            HBox hbox= (HBox) content.getChildren().get(0);
            ComboBox combo1=(ComboBox) hbox.getChildren().get(1);
            combo1.setOnAction(e -> {
            String a = (String) combo1.getValue();
            if (a != null) {
            delcombo=a;
             } else {
            System.out.println("Ningún elemento seleccionado");
             }
            });
        }else{
            System.out.println("No funciona");
        }
       
         btnEdit.setOnAction(e -> {
            editables();
           
        });
         
            
         
    } 
    public void editables(){
        if (attribute instanceof PhoneNumber){
            editarNumero();
        }else if(attribute instanceof Location){
            editarLocation();
        }else if(editar.equals("Email")){
            editarEmail();
        }else if(editar.equals("SocialMedia")){
            editarRedes();
        }else if(editar.equals("GenericReminder")){
            editarRecordatorio();
        }else if(editar.equals("AssociatedContact")){
            editAssociated();
        }
        
        super.sucessDialog();
        MobilePhone.updateContactList();
        try {
            super.returnContactPage(contact);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    

    public void editarNumero() {
        if (contactType == 'P'){
            HBox hbox= (HBox) content.getChildren().get(0);
            ComboBox comboBox = (ComboBox) hbox.getChildren().get(1);
            String combo = (String) comboBox.getSelectionModel().getSelectedItem();
            
            HBox hbox1= (HBox) content.getChildren().get(1);
            TextField t1=(TextField) hbox1.getChildren().get(1);
            PersonPhone ph = (PersonPhone) attribute;
            ph.setPhoneNumber(t1.getText());
            
            if(combo.equals("PERSONAL")){
                ph.setPhoneType(SourceType.PERSONAL);    
            }else{
                ph.setPhoneType(SourceType.WORK); 
            }    
           } else if (contactType == 'C'){
            HBox hbox1= (HBox) content.getChildren().get(0);
            TextField t1=(TextField) hbox1.getChildren().get(1);
            CompanyPhone ph = (CompanyPhone) attribute;
            ph.setPhoneNumber(t1.getText());
            
        }
                  
    }
    
    

    
    
    public void editarLocation(){
        if (contactType == 'P'){
            HBox hbox= (HBox) content.getChildren().get(0);
            ComboBox comboBox = (ComboBox) hbox.getChildren().get(1);
            String combo = (String) comboBox.getSelectionModel().getSelectedItem();
            
            HBox hbox1= (HBox) content.getChildren().get(1);
            HBox hbox2= (HBox) content.getChildren().get(2);
            TextField t1=(TextField) hbox1.getChildren().get(1);
            TextField t2=(TextField) hbox2.getChildren().get(1);
            PersonLocation pl = (PersonLocation) attribute;          
            pl.setDetails(t1.getText());
            pl.setMapsURL(t2.getText());
            if(combo.equals("PERSONAL")){

                pl.setLocationType(SourceType.PERSONAL);
            }else{
                pl.setLocationType(SourceType.WORK);
            }
            
            } else if (contactType == 'C'){
            HBox hbox1= (HBox) content.getChildren().get(0);
            HBox hbox2= (HBox) content.getChildren().get(1);
            TextField t1=(TextField) hbox1.getChildren().get(1);
            TextField t2=(TextField) hbox2.getChildren().get(1);
            CompanyLocation pl = (CompanyLocation) attribute;          
            pl.setDetails(t1.getText());
            pl.setMapsURL(t2.getText());
        }
            
        }
    
    public void editarRedes(){
        HBox hbox= (HBox) content.getChildren().get(0);
        HBox hbox1= (HBox) content.getChildren().get(1);
        TextField t1 = (TextField) hbox.getChildren().get(1);           
        ComboBox comboBox = (ComboBox) hbox1.getChildren().get(1);
        String combo = (String) comboBox.getSelectionModel().getSelectedItem();
        if(combo.equals("INSTAGRAM")){
            SocialMedia pl = (SocialMedia) attribute;              
            pl.setUser(t1.getText());             
            pl.setSocialMedia(SocialMediaType.INSTAGRAM);
        }else if(combo.equals("FACEBOOK")){
            SocialMedia pl = (SocialMedia) attribute;              
            pl.setUser(t1.getText());             
            pl.setSocialMedia(SocialMediaType.FACEBOOK);
        }else{
           SocialMedia pl = (SocialMedia) attribute;              
            pl.setUser(t1.getText());             
            pl.setSocialMedia(SocialMediaType.X);
        }  
    }
    
    public void editarEmail(){
        HBox hbox= (HBox) content.getChildren().get(0);
        HBox hbox1= (HBox) content.getChildren().get(1);
        ComboBox comboBox = (ComboBox) hbox.getChildren().get(1);
        TextField t1=(TextField) hbox1.getChildren().get(1);
        String combo = (String) comboBox.getSelectionModel().getSelectedItem();
        if(combo.equals("PERSONAL")){
            Email ph = (Email) attribute;  
            ph.setEmail(t1.getText());
            ph.setEmailType(SourceType.PERSONAL);    
        }else{
            Email ph = (Email) attribute;  
            ph.setEmail(t1.getText());
            ph.setEmailType(SourceType.WORK);  
        }  
    }
    
    public void editAssociated(){
        HBox hbox= (HBox) content.getChildren().get(0);
        HBox hbox1= (HBox) content.getChildren().get(1);
        ComboBox comboBox = (ComboBox) hbox.getChildren().get(1);
        TextField t1=(TextField) hbox1.getChildren().get(1);
        Contact combo = (Contact) comboBox.getSelectionModel().getSelectedItem();
        AssociatedContact ac = (AssociatedContact) attribute;
        ac.setContact(combo);
        ac.setRelation(t1.getText());

    }
    
    public void editarRecordatorio(){///revisar en empresa
        if(why){
        HBox hbox1= (HBox) content.getChildren().get(1);
            TextField t1=(TextField) hbox1.getChildren().get(1);         
            GenericReminder ph = (GenericReminder) attribute;  
            ph.setDescription(t1.getText());  
        }
    }
 
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }
    
}