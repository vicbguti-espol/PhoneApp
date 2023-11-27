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
import java.util.LinkedList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.attributes.AssociatedContact;
import model.attributes.ContactImage;
import model.attributes.Email;
import model.attributes.company.CompanyDescription;
import model.attributes.company.CompanyWebPage;
import model.attributes.location.Location;
import model.attributes.location.PersonLocation;
import model.attributes.names.CompanyName;
import model.attributes.names.PersonName;
import model.attributes.phone.PersonPhone;
import model.attributes.phone.PhoneNumber;
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
    private Button btn_volver;
    @FXML
    private BorderPane root;
    
    //contacto pasado
    private Contact contact;
    private Attribute attribute;
    
    public EditPresetAtributeController(Contact selectedContact, Attribute att) {
        contact = selectedContact;
        attribute =  att;
    }

    
    public void initialize(URL url, ResourceBundle rb) {
        mensaje.setText(attribute.getAttributeName());
         btn_volver.setOnAction(e -> {
             try {
                 returnContactPage();
             } catch (IOException ex) {
                 ex.printStackTrace();
             }
        });
         
         //root = new BorderPane();
         //HBox h = new HBox();
         //h.getChildren().addAll(btn_volver,mensaje);
         //root.setTop(h);
         //root.setBottom(root);
       /*String d1= new String("Telefono");
       String d2= new String("Direccion");
       tiposA.getItems().addAll(d1,d2);*/
       
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
        VBox content = new VBox();
        //Si implementa Typable
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
        
        root.setCenter(content);
       
    }
    
    
    @FXML
    private void btn_confirmar(MouseEvent event) {
        String dato= box_dato.getText();
        modificar=new ArrayList<>();
        modificar.add(contact);  
        Alista=modificar.get(0).attributes; 
        
        
        editar = attribute.getAttributeName();
        
        //MobilePhone.removeContact(contact);
        for(Attribute atributos:Alista){
            
            if(editar.equals("Location")){
             PersonLocation pl =(PersonLocation)atributos;
            
             pl.setDetails(dato);
             pl.setMapsURL(dato);
            }
            if( editar.equals("Telefono" )){
            PhoneNumber ph = (PhoneNumber) atributos;     
                ph.setPhoneNumber(dato);
            }
        }
       // MobilePhone.addContact(modificar.get(0));
        
    mensaje.setText("Cambio realizado");
        }
    

    @FXML
    private void seleccion(ActionEvent event) {

        ComboBox<String> cb = (ComboBox) event.getSource();
        String tipo = cb.getValue();
        editar=tipo;     
}

    @FXML
    private void g(MouseEvent event) {
    }
    
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }
    
}
