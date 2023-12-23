package application;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class gst_adh_scene implements Initializable{

	@FXML
	private TextField txtca;
	@FXML 
	private TextField txtnom;
	@FXML 
	private TextField txtprenom;
	@FXML
	private TextField txtadresse;
	@FXML
	private TextField txtcp;
	@FXML
	private TextField txttele;
	@FXML
	private TextField txtemail;
	@FXML
	private RadioButton rdtrue;
	@FXML
	private RadioButton rdfalse;
	@FXML
	private TextField txtgrp;
	@FXML
	private DatePicker dtp;
	@FXML
	private ImageView imgv;
	@FXML
	private Button btnnv;
	@FXML
	private Button btnmod;
	@FXML
	private Button btnsup;
	@FXML
	private TableView<Adherents> tvadh;
	@FXML
	private TableColumn<Adherents, Integer> clmca;
	@FXML
	private TableColumn<Adherents, String> clmnom;
	@FXML
	private TableColumn<Adherents, String> clmprenom;
	@FXML
	private TableColumn<Adherents, String> clmdtn;
	@FXML
	private TableColumn<Adherents, String> clmadresse;
	@FXML
	private TableColumn<Adherents, String> clmcp;
	@FXML
	private TableColumn<Adherents, Integer> clmtele;
	@FXML
	private TableColumn<Adherents, String> clmemail;
	@FXML
	private TableColumn<Adherents, String> clmpresence;
	@FXML
	private TableColumn<Adherents, Blob> clmphoto;
	@FXML
	private TableColumn<Adherents, String> clmgrp;
	
	ObservableList<Adherents> AdherentsObservableList =FXCollections.observableArrayList();
	
	public void refresh() {
		tvadh.getItems().clear();
		DBConnection connectnow = new DBConnection();
		Connection con = connectnow.sportclubcon();
		String cmd = "Select CodeAdherent,Nom,Prenom,DateDeNaissance,Adresse,CodePostal,Tele,Email,Presence,Photo,Groupe from adherents";
		try {
			Statement stm =con.createStatement();
	    	ResultSet rs = stm.executeQuery(cmd);
	    	while(rs.next()) {
	    		AdherentsObservableList.add(new Adherents(rs.getInt("CodeAdherent"),rs.getString("Nom"),rs.getString("Prenom"),rs.getDate("DateDeNaissance").toString(),rs.getString("Adresse"),rs.getString("CodePostal"), rs.getInt("Tele"),rs.getString("Email"),rs.getString("Presence"),rs.getBlob("Photo"),rs.getString("Groupe")));
	    	}
	    	
	    	clmca.setCellValueFactory(new PropertyValueFactory<>("CodeAdherent"));
	    	clmnom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
	    	clmprenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
	    	clmdtn.setCellValueFactory(new PropertyValueFactory<>("DateDeNaissance"));
	    	clmadresse.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
	    	clmcp.setCellValueFactory(new PropertyValueFactory<>("CodePostal"));
	    	clmtele.setCellValueFactory(new PropertyValueFactory<>("Tele"));
	    	clmemail.setCellValueFactory(new PropertyValueFactory<>("Email"));
	    	clmpresence.setCellValueFactory(new PropertyValueFactory<>("Presence"));
	    	clmphoto.setCellValueFactory(new PropertyValueFactory<>("Photo"));
	    	clmgrp.setCellValueFactory(new PropertyValueFactory<>("Groupe"));
	    	
	    	tvadh.setItems(AdherentsObservableList);

	    	
		}catch(SQLException e) {
			Logger.getLogger(gst_adh_scene.class.getName()).log(Level.SEVERE,null,e);
			e.printStackTrace();		
		}
		tvadh.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {	        
		        Adherents selected = tvadh.getSelectionModel().getSelectedItem();
		        txtca.setText(selected.getCodeAdherent().toString());	
		        txtnom.setText(selected.getNom().toString());
		        txtprenom.setText(selected.getPrenom().toString());
		        txtadresse.setText(selected.getAdresse().toString());
		        txtcp.setText(selected.getCodePostal().toString());
		        txttele.setText(selected.getTele().toString());
		        txtgrp.setText(selected.getGroupe().toString());
		        txtemail.setText(selected.getEmail().toString());
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        dtp.setValue(LocalDate.parse(selected.getDateDeNaissance().toString(),formatter));
                try {		     
                	InputStream input = (InputStream) selected.getPhoto().getBinaryStream();
					if (input != null && input.available() > 1) {
					    Image imge = new Image(input);
					    imgv.setImage(imge);
					}
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
		        String pr = selected.getPresence();
		        if(pr.equals("Present")) {
		        	rdtrue.setSelected(true); 
		        }else{
		        	rdfalse.setSelected(true);
		        }
		};
	});
	    tvadh.getSelectionModel().selectFirst();

	}
	
	public void initialize(URL url, ResourceBundle resource) {
		refresh();
   }
	public void disablecontrols() {
		   txtca.setDisable(true);
		     txtnom.setDisable(true);
		     txtprenom.setDisable(true);
		     txtadresse.setDisable(true);
		     txtcp.setDisable(true);
		     txttele.setDisable(true);
		     txtemail.setDisable(true);
		     txtgrp.setDisable(true);
		     rdtrue.setDisable(true);
		     rdfalse.setDisable(true);
		     dtp.setDisable(true);
	}
	public void enablecontrols() {
    txtca.setDisable(false);
    txtnom.setDisable(false);
    txtprenom.setDisable(false);
    txtadresse.setDisable(false);
    txtcp.setDisable(false);
    txttele.setDisable(false);
    txtemail.setDisable(false);
    txtgrp.setDisable(false);
    rdtrue.setDisable(false);
    rdfalse.setDisable(false);
    dtp.setDisable(false);
	}
	
	public void btnnvclick(ActionEvent e) throws ClassCastException, SQLException {
	if(btnnv.getText().equals("Nouveau")) {
	 enablecontrols();
     txtca.setText(null);
     txtnom.setText(null);
     txtprenom.setText(null);
     txtadresse.setText(null);
     txtcp.setText(null);
     txttele.setText(null);
     txtemail.setText(null);
     txtgrp.setText(null);
     rdtrue.setSelected(false);
     rdfalse.setSelected(false);
     dtp.setValue(null);
     imgv.setImage(null);
     btnnv.setText("Confirmer");
     btnmod.setText("Annuler");
     btnsup.setVisible(false);
     return;
     }else
     {if(txtca.getText()==""||txtadresse.getText()==""||txtnom.getText()==""||txtprenom.getText()==""||txtcp.getText()==""||txtgrp.getText()==""||dtp.getValue()==null||((rdtrue.isSelected() == false)&&(rdfalse.isSelected() == false))){
       Alert alert = new Alert(Alert.AlertType.ERROR);
       alert.setHeaderText(null);
       alert.setContentText("Veuillez remplir tous les champs!");
       alert.showAndWait();
     }else {
    	     btnnv.setText("Nouveau");
    	     btnmod.setText("Modifier");
    	     btnsup.setVisible(true);
    	     disablecontrols();
    	     insert();
    	     
    	     return;
           }     
     }
   }
     
	

	
	public void btnmodclick(ActionEvent e) throws SQLException{
		if (btnmod.getText().equals("Annuler")) {
          btnnv.setText("Nouveau");
          btnmod.setText("Modifier");
          btnsup.setVisible(true);
          disablecontrols();
  	    tvadh.getSelectionModel().selectNext();
  	    return;
		}
		if(btnmod.getText().equals("Modifier")) {
	          btnmod.setText("Confirmer");
	          btnsup.setText("Annuler");
	          btnnv.setVisible(false);
	          enablecontrols();
	          txtca.setDisable(true);
	          return;
	          }
		if(btnmod.getText().equals("Confirmer")) {
			update();
			disablecontrols();
			btnnv.setVisible(true);
			btnmod.setText("Modifier");
			btnsup.setText("Supprimer");
			return;
	     }
	            
		  	
	}	
	
	public void insert(){
		 try {
			String pr = null;
			 if(rdtrue.isSelected()) 
			 {
				 pr = rdtrue.getText();
			 }if (rdfalse.isSelected()) {
				 pr = rdfalse.getText();
			 } 
			DBConnection connectnow = new DBConnection();
			Connection con = connectnow.sportclubcon();
      String cmd ="INSERT INTO adherents (CodeAdherent,Nom,Prenom,DateDeNaissance,Adresse,CodePostal,Tele,Email,Presence,Groupe) VALUES ('"+txtca.getText()+"','"+txtnom.getText()+"','"+txtprenom.getText()+"','"+dtp.getValue()+"','"+txtadresse.getText()+"','"+txtcp.getText()+"','"+txttele.getText()+"','"+txtemail.getText()+"','"+pr+"','"+txtgrp.getText()+"')";
			Statement stm =con.createStatement();
  	stm.executeUpdate(cmd);
			refresh();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062)
		    {
			Alert alert = new Alert(AlertType.WARNING);
		      alert.setTitle("Error");
		      alert.setHeaderText("Le Code Adherent existe déja");
              alert.showAndWait();
              return;
		    }
			  Alert alert = new Alert(AlertType.WARNING);
		      alert.setTitle("Error");
		      alert.setHeaderText("Veuillez entrer des valeurs correctes");
             alert.showAndWait();
		}
       }
	
	public void update() {
		 try {
			String pr = null;
			 if(rdtrue.isSelected()) 
			 {
				 pr = rdtrue.getText();
			 }if (rdfalse.isSelected()) {
				 pr = rdfalse.getText();
			 } 
			DBConnection connectnow = new DBConnection();
			Connection con = connectnow.sportclubcon();
			String cmd ="UPDATE adherents SET Nom ='"+txtnom.getText()+"',Prenom='"+txtprenom.getText()+"',DateDeNaissance='"+dtp.getValue()+"',Adresse ='"+txtadresse.getText()+"',CodePostal='"+txtcp.getText()+"',Tele='"+txttele.getText()+"',Email='"+txtemail.getText()+"',Presence='"+pr+"',Groupe='"+txtgrp.getText()+"' WHERE CodeAdherent ='"+txtca.getText()+"'";
			Statement stm =con.createStatement();
			stm.executeUpdate(cmd);
			refresh();
		} catch (SQLException e) {
			  Alert alert = new Alert(AlertType.WARNING);
		      alert.setTitle("Error");
		      alert.setHeaderText("Veuillez entrer des valeurs correctes");
             alert.showAndWait();
		}
        }
	
	public void delete() {
		try {
			DBConnection connectnow = new DBConnection();
			Connection con = connectnow.sportclubcon();
			String cmd ="DELETE FROM adherents WHERE CodeAdherent ='"+ txtca.getText() +"'";
			Statement stm =con.createStatement();
			stm.executeUpdate(cmd);
			refresh();
		} catch (SQLException e) {
			 Alert alert = new Alert(AlertType.WARNING);
		      alert.setTitle("Error");
		      alert.setHeaderText("Cet adherent ne peut pas être supprimé!");
           alert.showAndWait();
		}
        }
	public void btnsupclick(ActionEvent e) throws ClassNotFoundException, SQLException {
		if (btnsup.getText().equals("Annuler")) {
	          btnsup.setText("Supprimer");
	          btnmod.setText("Modifier");
	          btnnv.setVisible(true);
	          disablecontrols();
	  	    tvadh.getSelectionModel().selectFirst();
	  	  return;
		}else { 
			Alert alert = new Alert(AlertType.CONFIRMATION);
	      alert.setTitle("Delete Adherent");
	      alert.setHeaderText("Etes-vous sûr de vouloir supprimer cet adherent?");

	      Optional<ButtonType> option = alert.showAndWait();
	      if (option.get() == ButtonType.OK) {
				delete();
				return;
	      } 
		
	   }
	}
	

}
