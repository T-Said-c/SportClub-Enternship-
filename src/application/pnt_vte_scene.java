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

public class pnt_vte_scene  implements Initializable {
	@FXML
	private TextField txtcp;
	@FXML 
	private TextField txtdes;
	@FXML
	private TextField txtbpj;
	@FXML
	private Button btnnv;
	@FXML
	private Button btnmod;
	@FXML
	private Button btnsup;
	@FXML
	private TableView<PointsVente> tvpv;
	@FXML
	private TableColumn<PointsVente, Integer> clmcp;
	@FXML
	private TableColumn<PointsVente, String> clmdes;
	@FXML
	private TableColumn<PointsVente, Double> clmbpj;
	ObservableList<PointsVente> PointsVenteObservableList =FXCollections.observableArrayList();

	public void refresh() {
		tvpv.getItems().clear();
		DBConnection connectnow = new DBConnection();
		Connection con = connectnow.sportclubcon();
		String cmd = "Select * from PointsVente";
		try {
			Statement stm =con.createStatement();
	    	ResultSet rs = stm.executeQuery(cmd);
	    	while(rs.next()) {
	    		PointsVenteObservableList.add(new PointsVente(rs.getInt("CodePoint"),rs.getString("Designation").toString(),rs.getDouble("BeneficeParJour")));
	    	}
	    	
	    	clmcp.setCellValueFactory(new PropertyValueFactory<>("CodePoint"));
	    	clmdes.setCellValueFactory(new PropertyValueFactory<>("Designation"));
	    	clmbpj.setCellValueFactory(new PropertyValueFactory<>("BeneficeParJour"));
	    	
	    	tvpv.setItems(PointsVenteObservableList);

	    	
		}catch(SQLException e) {
			Logger.getLogger(pnt_vte_scene.class.getName()).log(Level.SEVERE,null,e);
			e.printStackTrace();		
		}
		tvpv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {	        
		    	PointsVente selected = tvpv.getSelectionModel().getSelectedItem();
		        txtcp.setText(selected.getCodePoint().toString());	
		        txtdes.setText(selected.getDesignation().toString());
		        txtbpj.setText(selected.getBeneficeParJour().toString());

		};
	});
	    tvpv.getSelectionModel().selectFirst();

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		refresh();
	}
	public void disablecontrols() {
		   txtcp.setDisable(true);
		     txtdes.setDisable(true);
		     txtbpj.setDisable(true);
	}
	public void enablecontrols() {
		txtcp.setDisable(false);
		txtdes.setDisable(false);
		txtbpj.setDisable(false);
	}

	public void btnnvclick(ActionEvent e) throws ClassCastException, SQLException {
		if(btnnv.getText().equals("Nouveau")) {
		 enablecontrols();
	     txtcp.setText(null);
	     txtdes.setText(null);
	     txtbpj.setText(null);
	     btnnv.setText("Confirmer");
	     btnmod.setText("Annuler");
	     btnsup.setVisible(false);
	     return;
	     }else
	     {if(txtcp.getText()==""||txtdes.getText()==""||txtbpj.getText()==""){
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
	  	    tvpv.getSelectionModel().selectNext();
	  	    return;
			}
			if(btnmod.getText().equals("Modifier")) {
		          btnmod.setText("Confirmer");
		          btnsup.setText("Annuler");
		          btnnv.setVisible(false);
		          enablecontrols();
		          txtcp.setDisable(true);
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
            String cmd ="INSERT INTO PointsVente (CodePoint,Designation,BeneficeParJour) VALUES ('"+txtcp.getText()+"','"+txtdes.getText()+"','"+txtbpj.getText()+"')";
			Statement stm =con.createStatement();
			stm.executeUpdate(cmd);
			refresh();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062)
		    {
			Alert alert = new Alert(AlertType.WARNING);
		      alert.setTitle("Error");
		      alert.setHeaderText("Le Code Point existe déja");
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
			String cmd ="UPDATE PointsVente SET Designation ='"+txtdes.getText()+"',BeneficeParJour='"+txtbpj.getText()+"' WHERE CodePoint ='"+txtcp.getText()+"'";
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
			String cmd ="DELETE FROM PointsVente WHERE CodePoint ='"+ txtcp.getText() +"'";
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
	  	    tvpv.getSelectionModel().selectFirst();
	  	  return;
		}else { 
			Alert alert = new Alert(AlertType.CONFIRMATION);
	      alert.setTitle("Delete Point de Vente");
	      alert.setHeaderText("Etes-vous sûr de vouloir supprimer ce point de vente?");

	      Optional<ButtonType> option = alert.showAndWait();
	      if (option.get() == ButtonType.OK) {
				delete();
				return;
	      } 
		
	   }
	}

}
