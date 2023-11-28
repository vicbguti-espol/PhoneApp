package com.mycompany;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.attributes.Attribute;
import model.attributes.reminders.Reminder;
import model.contacts.Contact;
import model.user.MobilePhone;
import java.util.Comparator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.attributes.AssociatedContact;
import model.attributes.ContactImage;
import model.attributes.Email;
import model.attributes.SocialMedia;
import model.attributes.company.CompanyDescription;
import model.attributes.company.CompanyWebPage;
import model.attributes.location.Location;
import model.attributes.location.PersonLocation;
import model.attributes.names.CompanyName;
import model.attributes.names.PersonName;
import model.attributes.phone.PersonPhone;
import model.attributes.phone.PhoneNumber;
import model.attributes.reminders.GenericReminder;
import model.comparator.ComparatorUtil;
import model.enums.SocialMediaType;
import model.enums.SourceType;

public class EditPresetAtributeController extends Controller implements Initializable {

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
    @FXML
    private VBox content;
    @FXML
    private ComboBox<String> Universal;///poder elegir el tipo
    EditPresetAtributeController(Contact selectedContact, Attribute att) {
        contact = selectedContact;
        attribute = att;
    }

    
    public void initialize(URL url, ResourceBundle rb) {
       mensaje.setText(attribute.getAttributeName()); 
        btnReturn.setOnAction(e -> {
             try {
                 returnContactPage();
             } catch (IOException ex) {
                 ex.printStackTrace();
             }
        });
       
       ArrayList<Field> allFields = new ArrayList<>();
       Class<?> currentClass = attribute.getClass();
       int c=1;
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
        
         btnEdit.setOnAction(e -> {
            editables();
        });
         
    } 
    public void editables(){
        editar=attribute.getAttributeName();
        if (editar.equals("PersonPhone")){
            editarNumero();
        }else if(editar.equals("PersonLocation")){
            editarLocation();
        }else if(editar.equals("Email")){
            editarEmail();
        }else if(editar.equals("SocialMedia")){
            editarRedes();
        }else if(editar.equals("GenericReminder")){
            editarRecordatorio();
        }
        
    }
    
    public void editarNumero() { 
        //if(attribute.getAttributeName().equals()){
            //HBox hbox= (HBox) content.getChildren().get(0);
            
            HBox hbox1= (HBox) content.getChildren().get(1);
            TextField t1=(TextField) hbox1.getChildren().get(1);
            String combo="A";
            if(combo.equals("PERSONAL")){
                PersonPhone ph = (PersonPhone) attribute;  
                ph.setPhoneNumber(t1.getText());
                ph.setPhoneType(SourceType.PERSONAL);    
            }else{
                PersonPhone ph = (PersonPhone) attribute;  
                ph.setPhoneNumber(t1.getText());
                ph.setPhoneType(SourceType.WORK); 
            }               
    }
    
    public void editarLocation(){
        System.out.println(contact.getClass());
         HBox hbox1= (HBox) content.getChildren().get(1);
         HBox hbox2= (HBox) content.getChildren().get(2);
            TextField t1=(TextField) hbox1.getChildren().get(1);
            TextField t2=(TextField) hbox2.getChildren().get(1);
            String combo="A";
            if(combo.equals("PERSONAL")){
                PersonLocation pl = (PersonLocation) attribute;              
                pl.setDetails(t1.getText());
                pl.setMapsURL(t2.getText());
                pl.setLocationType(SourceType.PERSONAL);
            }else{
                PersonLocation pl = (PersonLocation) attribute;              
                pl.setDetails(t1.getText());
                pl.setMapsURL(t2.getText());
                pl.setLocationType(SourceType.WORK);
            }  
    }
    public void editarRedes(){
        HBox hbox1= (HBox) content.getChildren().get(1);
            TextField t1=(TextField) hbox1.getChildren().get(1);           
            String combo="A";
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
        HBox hbox1= (HBox) content.getChildren().get(1);
            TextField t1=(TextField) hbox1.getChildren().get(1);
            String combo="A";
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
    
    public void editarRecordatorio(){
        HBox hbox1= (HBox) content.getChildren().get(1);
            TextField t1=(TextField) hbox1.getChildren().get(1);         
            GenericReminder ph = (GenericReminder) attribute;  
            ph.setDescription(t1.getText());   
    }
    @FXML
    private void seleccion(ActionEvent event) {
          
}

    @FXML
    private void g(MouseEvent event) {
    }
    
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }
    
}
   //}
//       HBox hbox= (HBox) content.getChildren().get(1);
//        TextField t1=(TextField) hbox.getChildren().get(1);
//        System.out.println(t1.getText()); 
//         PhoneNumber ph = (PhoneNumber) attribute;   
//         
//               ph.setPhoneNumber(t1.getText());
//               editar = attribute.getAttributeName();//para saber quien es
//         System.out.println(editar+"si");
         
               
//        String dato= box_dato.getText();
////        if(dato==""||dato.equals("Dato")){
////            Alert alert = new Alert(AlertType.WARNING);
////        alert.setTitle("");
////        alert.setHeaderText(null);
////        alert.setContentText("Llenar el campo");
////
////        alert.showAndWait();
////        }else{
//        modificar=new ArrayList<>();
//        modificar.add(contact);  
//        Alista=modificar.get(0).attributes; 
//        
//        editar = attribute.getAttributeName();
//
//        //MobilePhone.removeContact(contact);
//        for(Attribute atributos:Alista){
//            if(editar.equals("Location")){
//                
//             PersonLocation pl =(PersonLocation)attribute;
//            
//             pl.setDetails(t1.getText());
//             pl.setMapsURL(dato);
//            }
//            if( editar.equals("Telefono" )){
//            PhoneNumber ph = (PhoneNumber) atributos;     
//                ph.setPhoneNumber(t1.getText());
//            }
//        //}
//       // MobilePhone.addContact(modificar.get(0));
//        
//    mensaje.setText("Cambio realizado");
//        }