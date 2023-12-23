package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class gst_uti_scene  implements Initializable {
	@FXML
	private TextField txtcu;
	@FXML 
	private TextField txtemail;
	@FXML
	private TextField txtmdp;
	@FXML
	private TextField txtpl;
	@FXML
	private Button btnnv;
	@FXML
	private Button btnmod;
	@FXML
	private Button btnsup;
	@FXML
	private TableView<Utilisateurs> tvuti;
	@FXML
	private TableColumn<Utilisateurs, Integer> clmcu;
	@FXML
	private TableColumn<Utilisateurs, String> clmemail;
	@FXML
	private TableColumn<Utilisateurs, String> clmmdp;
	@FXML
	private TableColumn<Utilisateurs, Integer> clmpl;
	ObservableList<Utilisateurs> UtilisateursObservableList =FXCollections.observableArrayList();

	public void refresh() {
		tvuti.getItems().clear();
		DBConnection connectnow = new DBConnection();
		Connection con = connectnow.sportclubcon();
		String cmd = "Select * from Utilisateurs";
		try {
			Statement stm =con.createStatement();
	    	ResultSet rs = stm.executeQuery(cmd);
	    	while(rs.next()) {
	    		UtilisateursObservableList.add(new Utilisateurs(rs.getInt("CodeUtilisateur"),rs.getString("Email"),rs.getString("MDP").toString(),rs.getInt("PermissionLevel")));
	    	}
	    	
	    	clmcu.setCellValueFactory(new PropertyValueFactory<>("CodeUtilisateur"));
	    	clmemail.setCellValueFactory(new PropertyValueFactory<>("Email"));
	    	clmmdp.setCellValueFactory(new PropertyValueFactory<>("MDP"));
	    	clmpl.setCellValueFactory(new PropertyValueFactory<>("PermissionLevel"));
	    	
	    	tvuti.setItems(UtilisateursObservableList);

	    	
		}catch(SQLException e) {
			Logger.getLogger(gst_uti_scene.class.getName()).log(Level.SEVERE,null,e);
			e.printStackTrace();		
		}
		tvuti.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {	        
		    	Utilisateurs selected = tvuti.getSelectionModel().getSelectedItem();
		        txtcu.setText(selected.getCodeUtilisateur().toString());	
		        txtemail.setText(selected.getEmail().toString());
		        txtmdp.setText(selected.getMDP().toString());	
		        txtpl.setText(selected.getPermissionLevel().toString());

		};
	});
	    tvuti.getSelectionModel().selectFirst();

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		refresh();
	}
	public void disablecontrols() {
		   txtcu.setDisable(true);
		     txtemail.setDisable(true);
		     txtmdp.setDisable(true);
		     txtpl.setDisable(true);
	}
	public void enablecontrols() {
		txtcu.setDisable(false);
		txtemail.setDisable(false);
		txtmdp.setDisable(false);
		txtpl.setDisable(false);
	}
	public void btnnvclick(ActionEvent e) throws ClassCastException, SQLException {
		if(btnnv.getText().equals("Nouveau")) {
		 enablecontrols();
	     txtcu.setText(null);
	     txtemail.setText(null);
	     txtmdp.setText(null);
	     txtpl.setText(null);
	     btnnv.setText("Confirmer");
	     btnmod.setText("Annuler");
	     btnsup.setVisible(false);
	     return;
	     }else
	     {if(txtcu.getText()==""||txtemail.getText()==""||txtmdp.getText()==""||txtpl.getText()==""){
	       Alert alert = new Alert(Alert.AlertType.ERROR);
	       alert.setHeaderText(null);
	       alert.setContentText("Veuillez remplir tous les champs!");
	       alert.showAndWait();
	     }else {
	    	     btnnv.setText("Nouveau");
	    	     btnmod.setText("Modifier");
	    	     btnsup.setVisible(true);
	    	     insert();
	    	     disablecontrols();
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
	  	    tvuti.getSelectionModel().selectNext();
	  	    return;
			}
			if(btnmod.getText().equals("Modifier")) {
		          btnmod.setText("Confirmer");
		          btnsup.setText("Annuler");
		          btnnv.setVisible(false);
		          enablecontrols();
		          txtcu.setDisable(true);
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
		
		public void insert() { 
			try {
				DBConnection connectnow = new DBConnection();
				Connection con = connectnow.sportclubcon();
                String cmd ="INSERT INTO Utilisateurs (CodeUtilisateur,Email,MDP,PermissionLevel) VALUES ('"+txtcu.getText()+"','"+txtemail.getText()+"','"+txtmdp.getText()+"','"+txtpl.getText()+"')";
				Statement stm =con.createStatement();
  	          stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				if (e.getErrorCode() == 1062)
			    {
				Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Le Code Utilisateur existe déja");
                  alert.showAndWait();
                  return;
			    }
				  Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Veuillez entrer des valeurs correctes");
                 alert.showAndWait();
			}
	       }
		
		public void update(){
			try {
				DBConnection connectnow = new DBConnection();
				Connection con = connectnow.sportclubcon();
				String cmd ="UPDATE Utilisateurs SET Email ='"+txtemail.getText()+"',MDP='"+txtmdp.getText()+"',PermissionLevel='"+txtpl.getText()+"' WHERE CodeUtilisateur ='"+txtcu.getText()+"'";
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
		
		public void delete(){
			try {
				DBConnection connectnow = new DBConnection();
				Connection con = connectnow.sportclubcon();
				String cmd ="DELETE FROM Utilisateurs WHERE CodeUtilisateur ='"+ txtcu.getText() +"'";
				Statement stm =con.createStatement();
				stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				 Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Cet Utilisateur ne peut pas être supprimé!");
                alert.showAndWait();
			}
	        }
		public void btnsupclick(ActionEvent e) throws ClassNotFoundException, SQLException {
			if (btnsup.getText().equals("Annuler")) {
		          btnsup.setText("Supprimer");
		          btnmod.setText("Modifier");
		          btnnv.setVisible(true);
		          disablecontrols();
		  	    tvuti.getSelectionModel().selectFirst();
		  	  return;
			}else { 
				Alert alert = new Alert(AlertType.CONFIRMATION);
		      alert.setTitle("Delete Utilisateur");
		      alert.setHeaderText("Etes-vous sûr de vouloir supprimer cet utilisateur?");

		      Optional<ButtonType> option = alert.showAndWait();
		      if (option.get() == ButtonType.OK) {
					delete();
					return;
		      } 
			
		   }
		}


}
