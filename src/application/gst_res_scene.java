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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class gst_res_scene  implements Initializable {
	@FXML
	private TextField txtre;
	@FXML 
	private TextField txtcad;
	@FXML
	private ChoiceBox<String> txthd;
	@FXML
	private ChoiceBox<String> txthf;
	@FXML 
	private DatePicker dtp;
	@FXML
	private Button btnnv;
	@FXML
	private Button btnmod;
	@FXML
	private Button btnsup;
	@FXML
	private TableView<Reservations> tvres;
	@FXML
	private TableColumn<Reservations, Integer> clmre;
	@FXML
	private TableColumn<Reservations, Integer> clmcad;
	@FXML
	private TableColumn<Reservations, String> clmhd;
	@FXML
	private TableColumn<Reservations, String> clmhf;
	@FXML
	private TableColumn<Reservations, String> clmdt;
	ObservableList<Reservations> ReservationsObservableList =FXCollections.observableArrayList();
	private String[] clock = {"9:00:00","10:00:00","11:00:00","12:00:00","13:00:00","14:00:00","15:00:00","16:00:00","17:00:00","18:00:00","19:00:00","20:00:00","21:00:00"};

	public void refresh() {
		DBConnection connectnow = new DBConnection();
		Connection con = connectnow.sportclubcon();
		String cmd = "Select * from Reservations";
		try {
			Statement stm =con.createStatement();
	    	ResultSet rs = stm.executeQuery(cmd);
	    	while(rs.next()) {
	    		ReservationsObservableList.add(new Reservations(rs.getInt("CodeReservation"),rs.getInt("CodeAdherent"),rs.getTime("HeureDebut").toString(),rs.getTime("HeureFin").toString(),rs.getDate("Date").toString()));
	    	}
	    	
	    	clmre.setCellValueFactory(new PropertyValueFactory<>("CodeReservation"));
	    	clmcad.setCellValueFactory(new PropertyValueFactory<>("CodeAdherent"));
	    	clmhd.setCellValueFactory(new PropertyValueFactory<>("HeureDebut"));
	    	clmhf.setCellValueFactory(new PropertyValueFactory<>("HeureFin"));
	    	clmdt.setCellValueFactory(new PropertyValueFactory<>("Date"));
	    	
	    	tvres.setItems(ReservationsObservableList);

	    	
		}catch(SQLException e) {
			Logger.getLogger(gst_res_scene.class.getName()).log(Level.SEVERE,null,e);
			e.printStackTrace();		
		}
		tvres.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {	        
		    	Reservations selected = tvres.getSelectionModel().getSelectedItem();
		        txtre.setText(selected.getCodeReservation().toString());	
		        txtcad.setText(selected.getCodeAdherent().toString());
		        txthd.setValue(selected.getHeureDebut().toString());
		        txthf.setValue(selected.getHeureFin().toString());
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        dtp.setValue(LocalDate.parse(selected.getDate().toString(),formatter));
		};
	});
	    tvres.getSelectionModel().selectFirst();

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		txthd.getItems().addAll(clock);
		txthf.getItems().addAll(clock);
		refresh();
	}
	public void disablecontrols() {
		   txtre.setDisable(true);
		     txtcad.setDisable(true);
		     txthd.setDisable(true);
		     txthf.setDisable(true);
		     dtp.setDisable(true);
	}
	public void enablecontrols() {
		   txtre.setDisable(false);
		     txtcad.setDisable(false);
		     txthd.setDisable(false);
		     txthf.setDisable(false);
		     dtp.setDisable(false);
	}

	public void btnnvclick(ActionEvent e) throws ClassCastException, SQLException {
		if(btnnv.getText().equals("Nouveau")) {
		 enablecontrols();
	     txtre.setText(null);
	     txtcad.setText(null);
	     txthd.setValue(null);
	     txthf.setValue(null);
	     dtp.setValue(null);
	     btnnv.setText("Confirmer");
	     btnmod.setText("Annuler");
	     btnsup.setVisible(false);
	     return;
	     }else
	     {if(txtre.getText()==""||txtcad.getText()==""||txthd.getValue()==""||txthf.getValue()==""||dtp.getValue() == null){
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
	  	    tvres.getSelectionModel().selectNext();
	  	    return;
			}
			if(btnmod.getText().equals("Modifier")) {
		          btnmod.setText("Confirmer");
		          btnsup.setText("Annuler");
		          btnnv.setVisible(false);
		          enablecontrols();
		          txtre.setDisable(true);
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
                String cmd ="INSERT INTO Reservations (CodeReservation,CodeAdherent,HeureDebut,HeureFin,Date) VALUES ('"+txtre.getText()+"','"+txtcad.getText()+"','"+txthd.getValue()+"','"+txthf.getValue()+"','"+dtp.getValue()+"')";
				Statement stm =con.createStatement();
				stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				if (e.getErrorCode() == 1062)
			    {
				Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Le Code Reservation existe déja");
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
	       
		
		public void update(){
			try {
				DBConnection connectnow = new DBConnection();
				Connection con = connectnow.sportclubcon();
				String cmd ="UPDATE Reservations SET HeureDebut ='"+txthd.getValue()+"',HeureFin='"+txthf.getValue()+"',Date='"+dtp.getValue()+"' WHERE CodeReservation ='"+txtre.getText()+"'";
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
				String cmd ="DELETE FROM Reservations WHERE CodeReservation ='"+ txtre.getText() +"'";
				Statement stm =con.createStatement();
				stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				 Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Cette Reservation ne peut pas être supprimé!");
               alert.showAndWait();
			}
	      }
		
		public void btnsupclick(ActionEvent e) throws ClassNotFoundException, SQLException {
			if (btnsup.getText().equals("Annuler")) {
		          btnsup.setText("Supprimer");
		          btnmod.setText("Modifier");
		          btnnv.setVisible(true);
		          disablecontrols();
		  	    tvres.getSelectionModel().selectFirst();
		  	  return;
			}else { 
				Alert alert = new Alert(AlertType.CONFIRMATION);
		      alert.setTitle("Delete Reservation");
		      alert.setHeaderText("Etes-vous sûr de vouloir supprimer cette reservation?");
		      Optional<ButtonType> option = alert.showAndWait();
		      if (option.get() == ButtonType.OK) {
					delete();
					return;
		      } 
			
		   }
		}

}
