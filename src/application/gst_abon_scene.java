package application;

import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class gst_abon_scene  implements Initializable {
	@FXML
	private TextField txtcab;
	@FXML 
	private TextField txtcad;
	@FXML
	private DatePicker dtpex;
	@FXML
	private DatePicker dtpcr;
	@FXML
	private Button btnnv;
	@FXML
	private Button btnmod;
	@FXML
	private Button btnsup;
	@FXML
	private TableView<Abonnements> tvabo;
	@FXML
	private TableColumn<Abonnements, Integer> clmcab;
	@FXML
	private TableColumn<Abonnements, Integer> clmcad;
	@FXML
	private TableColumn<Abonnements, String> clmdcr;
	@FXML
	private TableColumn<Abonnements, String> clmdex;
	ObservableList<Abonnements> AbonnementsObservableList =FXCollections.observableArrayList();

	public void refresh() {
		tvabo.getItems().clear();
		DBConnection connectnow = new DBConnection();
		Connection con = connectnow.sportclubcon();
		String cmd = "Select * from abonnements";
		try {
			Statement stm =con.createStatement();
	    	ResultSet rs = stm.executeQuery(cmd);
	    	while(rs.next()) {
	    		AbonnementsObservableList.add(new Abonnements(rs.getInt("CodeAbonnement"),rs.getInt("CodeAdherent"),rs.getString("DateDeCreation").toString(),rs.getDate("DateExpiration").toString()));
	    	}
	    	
	    	clmcab.setCellValueFactory(new PropertyValueFactory<>("CodeAbonnement"));
	    	clmcad.setCellValueFactory(new PropertyValueFactory<>("CodeAdherent"));
	    	clmdcr.setCellValueFactory(new PropertyValueFactory<>("DateDeCreation"));
	    	clmdex.setCellValueFactory(new PropertyValueFactory<>("DateExpiration"));
	    	
	    	tvabo.setItems(AbonnementsObservableList);

	    	
		}catch(SQLException e) {
			Logger.getLogger(gst_abon_scene.class.getName()).log(Level.SEVERE,null,e);
			e.printStackTrace();		
		}
		tvabo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {	        
		        Abonnements selected = tvabo.getSelectionModel().getSelectedItem();
		        txtcab.setText(selected.getCodeAbonnement().toString());	
		        txtcad.setText(selected.getCodeAdherent().toString());
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        dtpcr.setValue(LocalDate.parse(selected.getDateDeCreation().toString(),formatter));
		        dtpex.setValue(LocalDate.parse(selected.getDateExpiration().toString(),formatter));
		};
	});
	    tvabo.getSelectionModel().selectFirst();

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		refresh();
	}
	public void disablecontrols() {
		   txtcab.setDisable(true);
		     txtcad.setDisable(true);
		     dtpcr.setDisable(true);
		     dtpex.setDisable(true);
	}
	public void enablecontrols() {
		txtcab.setDisable(false);
		txtcad.setDisable(false);
		dtpcr.setDisable(false);
		dtpex.setDisable(false);
	}
	public void btnnvclick(ActionEvent e) throws ClassCastException, SQLException {
		if(btnnv.getText().equals("Nouveau")) {
		 enablecontrols();
	     txtcab.setText(null);
	     txtcad.setText(null);
	     dtpcr.setValue(null);
	     dtpex.setValue(null);
	     btnnv.setText("Confirmer");
	     btnmod.setText("Annuler");
	     btnsup.setVisible(false);
	     return;
	     }else
	     {if(txtcab.getText()==""||txtcad.getText()==""||dtpcr.getValue()== null||dtpex.getValue()==  null){
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
	  	    tvabo.getSelectionModel().selectNext();
	  	    return;
			}
			if(btnmod.getText().equals("Modifier")) {
		          btnmod.setText("Confirmer");
		          btnsup.setText("Annuler");
		          btnnv.setVisible(false);
		          enablecontrols();
		          txtcab.setDisable(true);
		          txtcad.setDisable(true);
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
                String cmd ="INSERT INTO Abonnements (CodeAbonnement,CodeAdherent,DateDeCreation,DateExpiration) VALUES ('"+txtcab.getText()+"','"+txtcad.getText()+"','"+dtpcr.getValue()+"','"+dtpex.getValue()+"')";
				Statement stm =con.createStatement();
				stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				if (e.getErrorCode() == 1062)
			    {
				Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Le Code Abonnement existe déja");
                  alert.showAndWait();
                  return;
			    }
				if (e.getErrorCode() == 1452) {
				Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Cet adherent n'existe pas");
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
				DBConnection connectnow = new DBConnection();
				Connection con = connectnow.sportclubcon();
				String cmd ="UPDATE Abonnements SET DateDeCreation ='"+dtpcr.getValue()+"',DateExpiration='"+dtpex.getValue()+"' WHERE CodeAbonnement ='"+txtcab.getText()+"'";
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
		
		public void delete() throws SQLException{
			DBConnection connectnow = new DBConnection();
			Connection con = connectnow.sportclubcon();
	        String cmd ="DELETE FROM Abonnements WHERE CodeAbonnement ='"+ txtcab.getText() +"'";
			Statement stm =con.createStatement();
	    	stm.executeUpdate(cmd);
			refresh();
	        }
		public void btnsupclick(ActionEvent e) throws ClassNotFoundException, SQLException {
			if (btnsup.getText().equals("Annuler")) {
		          btnsup.setText("Supprimer");
		          btnmod.setText("Modifier");
		          btnnv.setVisible(true);
		          disablecontrols();
		  	    tvabo.getSelectionModel().selectFirst();
		  	  return;
			}else { 
				Alert alert = new Alert(AlertType.CONFIRMATION);
		      alert.setTitle("Delete Abonnement");
		      alert.setHeaderText("Etes-vous sûr de vouloir supprimer cet abonnement?");

		      Optional<ButtonType> option = alert.showAndWait();
		      if (option.get() == ButtonType.OK) {
					delete();
					return;
		      } 
			
		   }
		}

}
