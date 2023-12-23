package application;

import java.io.IOException;
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

public class gst_per_scene implements Initializable {
	@FXML
	private TextField txtcp;
	@FXML 
	private TextField txtnom;
	@FXML
	private TextField txtpre;
	@FXML
	private TextField txtsal;
	@FXML 
	private TextArea txtfm;
	@FXML
	private Button btnnv;
	@FXML
	private Button btnmod;
	@FXML
	private Button btnsup;
	@FXML
	private TableView<Personnels> tvper;
	@FXML
	private TableColumn<Personnels, Integer> clmcp;
	@FXML
	private TableColumn<Personnels, Integer> clmnom;
	@FXML
	private TableColumn<Personnels, String> clmprenom;
	@FXML
	private TableColumn<Personnels, String> clmsal;
	@FXML
	private TableColumn<Personnels, String> clmfm;
	
	ObservableList<Personnels> PersonnelsObservableList =FXCollections.observableArrayList();

	public void refresh() {
		tvper.getItems().clear();
		DBConnection connectnow = new DBConnection();
		Connection con = connectnow.sportclubcon();
		String cmd = "Select * from Personnels";
		try {
			Statement stm =con.createStatement();
	    	ResultSet rs = stm.executeQuery(cmd);
	    	while(rs.next()) {
	    		PersonnelsObservableList.add(new Personnels(rs.getInt("CodePersonnel"),rs.getString("Nom"),rs.getString("Prenom"),rs.getDouble("Salaire"),rs.getString("FaitsMarquants")));
	    	}
	    	
	    	clmcp.setCellValueFactory(new PropertyValueFactory<>("CodePersonnel"));
	    	clmnom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
	    	clmprenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
	    	clmsal.setCellValueFactory(new PropertyValueFactory<>("Salaire"));
	    	clmfm.setCellValueFactory(new PropertyValueFactory<>("FaitsMarquants"));
	    	
	    	tvper.setItems(PersonnelsObservableList);

	    	
		}catch(SQLException e) {
			Logger.getLogger(gst_per_scene.class.getName()).log(Level.SEVERE,null,e);
			e.printStackTrace();		
		}
		tvper.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {	        
		    	Personnels selected = tvper.getSelectionModel().getSelectedItem();
		        txtcp.setText(selected.getCodePersonnel().toString());	
		        txtnom.setText(selected.getNom().toString());
		        txtpre.setText(selected.getPrenom().toString());
		        txtsal.setText(selected.getSalaire().toString());
		        txtfm.setText(selected.getFaitsMarquants().toString());

		};
	});
	    tvper.getSelectionModel().selectFirst();

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		refresh();
	}
	public void disablecontrols() {
		   txtcp.setDisable(true);
		     txtnom.setDisable(true);
		     txtpre.setDisable(true);
		     txtsal.setDisable(true);
		     txtfm.setDisable(true);
	}
	public void enablecontrols() {
		   txtcp.setDisable(false);
		     txtnom.setDisable(false);
		     txtpre.setDisable(false);
		     txtsal.setDisable(false);
		     txtfm.setDisable(false);
	}
	
	public void btnnvclick(ActionEvent e) throws ClassCastException, SQLException {
		if(btnnv.getText().equals("Nouveau")) {
		 enablecontrols();
	     txtcp.setText(null);
	     txtnom.setText(null);
	     txtpre.setText(null);
	     txtsal.setText(null);
	     txtfm.setText(null);
	     btnnv.setText("Confirmer");
	     btnmod.setText("Annuler");
	     btnsup.setVisible(false);
	     return;
	     }else
	     {if(txtcp.getText()==""||txtnom.getText()==""||txtpre.getText()==""||txtsal.getText()==""||txtfm.getText()==""){
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
	  	    tvper.getSelectionModel().selectNext();
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
                String cmd ="INSERT INTO Personnels (CodePersonnel,Nom,Prenom,Salaire,FaitsMarquants) VALUES ('"+txtcp.getText()+"','"+txtnom.getText()+"','"+txtpre.getText()+"','"+txtsal.getText()+"','"+txtfm.getText()+"')";
				Statement stm =con.createStatement();
  	          stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				if (e.getErrorCode() == 1062)
			    {
				Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Le Code Personnel existe déja");
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
				String cmd ="UPDATE Personnels SET Nom ='"+txtnom.getText()+"',Prenom='"+txtpre.getText()+"',Salaire='"+txtsal.getText()+"',FaitsMarquants='"+txtfm.getText()+"' WHERE CodePersonnel ='"+txtcp.getText()+"'";
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
				String cmd ="DELETE FROM Personnels WHERE CodePersonnel ='"+ txtcp.getText() +"'";
				Statement stm =con.createStatement();
				stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				 Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Ce personnel ne peut pas être supprimé!");
                alert.showAndWait();
			}
	        }
		public void btnsupclick(ActionEvent e) throws ClassNotFoundException, SQLException {
			if (btnsup.getText().equals("Annuler")) {
		          btnsup.setText("Supprimer");
		          btnmod.setText("Modifier");
		          btnnv.setVisible(true);
		          disablecontrols();
		  	    tvper.getSelectionModel().selectFirst();
		  	  return;
			}else { 
				Alert alert = new Alert(AlertType.CONFIRMATION);
		      alert.setTitle("Delete Personnel");
		      alert.setHeaderText("Etes-vous sûr de vouloir supprimer ce personnel?");

		      Optional<ButtonType> option = alert.showAndWait();
		      if (option.get() == ButtonType.OK) {
					delete();
					return;
		      } 
			
		   }
		}

		public void btnexclick(ActionEvent e) throws IOException, SQLException {
//			DBConnection connectnow = new DBConnection();
//			Connection con = connectnow.sportclubcon();
//			String cmd ="SELECT * FROM Personnels";
//			Statement stm =con.createStatement();
//	    	ResultSet rs = stm.executeQuery(cmd);
//	        XSSFWorkbook wb = new XSSFWorkbook();
//            XSSFSheet sheet = wb.createSheet("DataBase Sheet");
//	        XSSFRow header  =  sheet.createRow(0);
//            header.createCell(0).setCellValue("Code Personnel");
//            header.createCell(1).setCellValue("Nom");
//            header.createCell(2).setCellValue("Prenom");
//            header.createCell(3).setCellValue("Salaire");
//            header.createCell(4).setCellValue("Faits Marquants");
//
//            int index = 1;
//	    	while(rs.next()) {
//	    		XSSFRow row = sheet.createRow(index);
//	            row.createCell(0).setCellValue("CodePersonnel");
//	            header.createCell(1).setCellValue("Nom");
//	            header.createCell(2).setCellValue("Prenom");
//	            header.createCell(3).setCellValue("Salaire");
//	            header.createCell(4).setCellValue("FaitsMarquants");
//	            index++;
//	    	}
//			
//	        FileOutputStream fileOut = new FileOutputStream("Data Personnels.xlsx");
//	        wb.write(fileOut);
//	        fileOut.close();
//	        stm.close();
//	        rs.close();
//	        Platform.exit();

	    }
		

}
