package com.mycompany;

import java.io.IOException;
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
import javafx.scene.control.ComboBox;
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
    
    //contacto pasado
    private Contact contact;
    
    public EditPresetAtributeController(Contact selectedContact) {
        contact = selectedContact;
    }

    
    public void initialize(URL url, ResourceBundle rb) {
         btn_volver.setOnAction(e -> {
             try {
                 returnContactPage();
             } catch (IOException ex) {
                 ex.printStackTrace();
             }
        });
       String d1= new String("Telefono");
       String d2= new String("Direccion");
       tiposA.getItems().addAll(d1,d2);
    }
    
    
    @FXML
    private void btn_confirmar(MouseEvent event) {
    Boolean confirma=true;
    mensaje.setText("Cambio realizado");
        }
    

    @FXML
    private void seleccion(ActionEvent event) {
        String dato= box_dato.getText();
        ComboBox<String> cb = (ComboBox) event.getSource();
        String tipo = cb.getValue();
       
        modificar=new ArrayList<>();
        modificar.add(contact);  
        Alista=modificar.get(0).attributes; 
        if(confirma){
        MobilePhone.removeContact(contact);
        for(Attribute atributos:Alista){
            if(tipo.equals("Direccion")){
             Location loc = (Location) atributos;
             loc.setDetails(dato);
            }
            if( tipo.equals("Telefono" )){
            PhoneNumber ph = (PhoneNumber) atributos;     
                ph.setPhoneNumber(dato);
            }
        }
        MobilePhone.addContact(modificar.get(0));
        
        }
}

    @FXML
    private void g(MouseEvent event) {
    }
    
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }
    
}
