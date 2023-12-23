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

public class gst_dep_scene implements Initializable  {
	@FXML
	private TextField txtcd;
	@FXML 
	private TextArea txtdet;
	@FXML
	private TextField txtsom;
	@FXML
	private Button btnnv;
	@FXML
	private Button btnmod;
	@FXML
	private Button btnsup;
	@FXML
	private TableView<Depenses> tvdep;
	@FXML
	private TableColumn<Depenses, Integer> clmcd;
	@FXML
	private TableColumn<Depenses, String> clmdet;
	@FXML
	private TableColumn<Depenses, Double> clmsom;
	
	ObservableList<Depenses> DepensesObservableList =FXCollections.observableArrayList();

	public void refresh() {
		tvdep.getItems().clear();
		DBConnection connectnow = new DBConnection();
		Connection con = connectnow.sportclubcon();
		String cmd = "Select * from depenses";
		try {
			Statement stm =con.createStatement();
	    	ResultSet rs = stm.executeQuery(cmd);
	    	while(rs.next()) {
	    		DepensesObservableList.add(new Depenses(rs.getInt("CodeDepense"),rs.getString("DetailDepense"),rs.getDouble("Somme")));
	    	}
	    	
	    	clmcd.setCellValueFactory(new PropertyValueFactory<>("CodeDepense"));
	    	clmdet.setCellValueFactory(new PropertyValueFactory<>("DetailDepense"));
	    	clmsom.setCellValueFactory(new PropertyValueFactory<>("Somme"));
	    	
	    	tvdep.setItems(DepensesObservableList);

	    	
		}catch(SQLException e) {
			Logger.getLogger(gst_dep_scene.class.getName()).log(Level.SEVERE,null,e);
			e.printStackTrace();		
		}
		tvdep.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {	        
		    	Depenses selected = tvdep.getSelectionModel().getSelectedItem();
		        txtcd.setText(selected.getCodeDepense().toString());	
		        txtdet.setText(selected.getDetailDepense().toString());
		        txtsom.setText(selected.getSomme().toString());

		};
	});
	    tvdep.getSelectionModel().selectFirst();

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		refresh();
	}
	public void disablecontrols() {
		   txtcd.setDisable(true);
		     txtdet.setDisable(true);
		     txtsom.setDisable(true);
	}
	public void enablecontrols() {
		txtcd.setDisable(false);
		txtdet.setDisable(false);
		txtsom.setDisable(false);
	}
	public void btnnvclick(ActionEvent e) throws ClassCastException, SQLException {
		if(btnnv.getText().equals("Nouveau")) {
		 enablecontrols();
	     txtcd.setText(null);
	     txtdet.setText(null);
	     txtsom.setText(null);
	     btnnv.setText("Confirmer");
	     btnmod.setText("Annuler");
	     btnsup.setVisible(false);
	     return;
	     }else
	     {if(txtcd.getText()==""||txtdet.getText()==""||txtsom.getText()==""){
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
	  	    tvdep.getSelectionModel().selectNext();
	  	    return;
			}
			if(btnmod.getText().equals("Modifier")) {
		          btnmod.setText("Confirmer");
		          btnsup.setText("Annuler");
		          btnnv.setVisible(false);
		          enablecontrols();
		          txtcd.setDisable(true);
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
				DBConnection connectnow = new DBConnection();
				Connection con = connectnow.sportclubcon();
      String cmd ="INSERT INTO Depenses (CodeDepense,DetailDepense,Somme) VALUES ('"+txtcd.getText()+"','"+txtdet.getText()+"','"+txtsom.getText()+"')";
				Statement stm =con.createStatement();
				stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				if (e.getErrorCode() == 1062)
			    {
				Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Le Code Depense existe déja");
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
				String cmd ="UPDATE Depenses SET DetailDepense ='"+txtdet.getText()+"',Somme='"+txtsom.getText()+"' WHERE CodeDepense ='"+txtcd.getText()+"'";
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
				String cmd ="DELETE FROM Depenses WHERE CodeDepense ='"+ txtcd.getText() +"'";
				Statement stm =con.createStatement();
				stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Cette Depense ne peut pas être supprimé!");
              alert.showAndWait();
			}
	        }
		public void btnsupclick(ActionEvent e) throws ClassNotFoundException, SQLException {
			if (btnsup.getText().equals("Annuler")) {
		          btnsup.setText("Supprimer");
		          btnmod.setText("Modifier");
		          btnnv.setVisible(true);
		          disablecontrols();
		  	    tvdep.getSelectionModel().selectFirst();
		  	  return;
			}else { 
				Alert alert = new Alert(AlertType.CONFIRMATION);
		      alert.setTitle("Delete Depense");
		      alert.setHeaderText("Etes-vous sûr de vouloir supprimer ce depense?");

		      Optional<ButtonType> option = alert.showAndWait();
		      if (option.get() == ButtonType.OK) {
					delete();
					return;
		      } 
			
		   }
		}
}
