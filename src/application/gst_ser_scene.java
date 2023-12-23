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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class gst_ser_scene  implements Initializable {
	@FXML
	private TextField txtser;
	@FXML 
	private TextArea txtdet;
	@FXML
	private TextField txtpai;
	@FXML
	private Button btnnv;
	@FXML
	private Button btnmod;
	@FXML
	private Button btnsup;
	@FXML
	private TableView<Services> tvser;
	@FXML
	private TableColumn<Services, Integer> clmser;
	@FXML
	private TableColumn<Services, String> clmdet;
	@FXML
	private TableColumn<Services, Double> clmpai;
	ObservableList<Services> ServicesObservableList =FXCollections.observableArrayList();

	public void refresh() {
		tvser.getItems().clear();
		DBConnection connectnow = new DBConnection();
		Connection con = connectnow.sportclubcon();
		String cmd = "Select * from Services";
		try {
			Statement stm =con.createStatement();
	    	ResultSet rs = stm.executeQuery(cmd);
	    	while(rs.next()) {
	    		ServicesObservableList.add(new Services(rs.getInt("CodeService"),rs.getString("Detail").toString(),rs.getDouble("Paiement")));
	    	}
	    	
	    	clmser.setCellValueFactory(new PropertyValueFactory<>("CodeService"));
	    	clmdet.setCellValueFactory(new PropertyValueFactory<>("Detail"));
	    	clmpai.setCellValueFactory(new PropertyValueFactory<>("Paiement"));
	    	
	    	tvser.setItems(ServicesObservableList);

	    	
		}catch(SQLException e) {
			Logger.getLogger(gst_ser_scene.class.getName()).log(Level.SEVERE,null,e);
			e.printStackTrace();		
		}
		tvser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {	        
		    	Services selected = tvser.getSelectionModel().getSelectedItem();
		        txtser.setText(selected.getCodeService().toString());	
		        txtdet.setText(selected.getDetail().toString());
		        txtpai.setText(selected.getPaiement().toString());

		};
	});
	    tvser.getSelectionModel().selectFirst();

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		refresh();
	}
	public void disablecontrols() {
		   txtser.setDisable(true);
		     txtdet.setDisable(true);
		     txtpai.setDisable(true);
	}
	public void enablecontrols() {
		txtser.setDisable(false);
		txtdet.setDisable(false);
		txtpai.setDisable(false);
	}

	public void btnnvclick(ActionEvent e) throws ClassCastException, SQLException {
		if(btnnv.getText().equals("Nouveau")) {
		 enablecontrols();
	     txtser.setText(null);
	     txtdet.setText(null);
	     txtpai.setText(null);
	     btnnv.setText("Confirmer");
	     btnmod.setText("Annuler");
	     btnsup.setVisible(false);
	     return;
	     }else
	     {if(txtser.getText()==""||txtdet.getText()==""||txtpai.getText()==""){
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
	  	    tvser.getSelectionModel().selectNext();
	  	    return;
			}
			if(btnmod.getText().equals("Modifier")) {
		          btnmod.setText("Confirmer");
		          btnsup.setText("Annuler");
		          btnnv.setVisible(false);
		          enablecontrols();
		          txtser.setDisable(true);
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
      String cmd ="INSERT INTO Services (CodeService,Detail,Paiement) VALUES ('"+txtser.getText()+"','"+txtdet.getText()+"','"+txtpai.getText()+"')";
				Statement stm =con.createStatement();
				stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				if (e.getErrorCode() == 1062)
			    {
				Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Le Code Service existe déja");
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
				String cmd ="UPDATE Services SET Detail ='"+txtdet.getText()+"',Paiement='"+txtpai.getText()+"' WHERE CodeService ='"+txtser.getText()+"'";
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
				String cmd ="DELETE FROM Services WHERE CodeService ='"+ txtser.getText() +"'";
				Statement stm =con.createStatement();
				stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				 Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Ce Service ne peut pas être supprimé!");
               alert.showAndWait();
			}
	      }
		
		public void btnsupclick(ActionEvent e) throws ClassNotFoundException, SQLException {
			if (btnsup.getText().equals("Annuler")) {
		          btnsup.setText("Supprimer");
		          btnmod.setText("Modifier");
		          btnnv.setVisible(true);
		          disablecontrols();
		  	    tvser.getSelectionModel().selectFirst();
		  	  return;
			}else { 
				Alert alert = new Alert(AlertType.CONFIRMATION);
		      alert.setTitle("Delete Service");
		      alert.setHeaderText("Etes-vous sûr de vouloir supprimer ce service?");

		      Optional<ButtonType> option = alert.showAndWait();
		      if (option.get() == ButtonType.OK) {
					delete();
					return;
		      } 
			
		   }
		}

}
