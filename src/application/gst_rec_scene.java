package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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

public class gst_rec_scene  implements Initializable {
	@FXML
	private TextField txtcr;
	@FXML 
	private TextField txtca;
	@FXML
	private ChoiceBox<String> txtmp;
	@FXML
	private TextField txtmon;
	@FXML
	private DatePicker dtpec;
	@FXML
	private Button btnnv;
	@FXML
	private Button btnmod;
	@FXML
	private Button btnsup;
	@FXML
	private TableView<Recettes> tvrec;
	@FXML
	private TableColumn<Recettes, Integer> clmrec;
	@FXML
	private TableColumn<Recettes, Integer> clmadh;
	@FXML
	private TableColumn<Recettes, String> clmmp;
	@FXML
	private TableColumn<Recettes, Double> clmmon;
	@FXML
	private TableColumn<Recettes, String> clmdec;
	
	ObservableList<Recettes> RecettesObservableList =FXCollections.observableArrayList();

	private String[] MP = {"Carte Bancaire","Espéces","Chèque"};

	public void refresh() {
		tvrec.getItems().clear();
		DBConnection connectnow = new DBConnection();
		Connection con = connectnow.sportclubcon();
		String cmd = "Select * from Recettes";
		try {
			Statement stm =con.createStatement();
	    	ResultSet rs = stm.executeQuery(cmd);
	    	while(rs.next()) {
	    		RecettesObservableList.add(new Recettes(rs.getInt("CodeRecette"),rs.getInt("CodeAdherent"),rs.getString("ModePaiement").toString(),rs.getDouble("Montant"),rs.getString("DateEcheance")));
	    	}
	    	
	    	clmrec.setCellValueFactory(new PropertyValueFactory<>("CodeRecette"));
	    	clmadh.setCellValueFactory(new PropertyValueFactory<>("CodeAdherent"));
	    	clmmp.setCellValueFactory(new PropertyValueFactory<>("ModePaiement"));
	    	clmmon.setCellValueFactory(new PropertyValueFactory<>("Montant"));
	    	clmdec.setCellValueFactory(new PropertyValueFactory<>("DateEcheance"));

	    	
	    	tvrec.setItems(RecettesObservableList);

	    	
		}catch(SQLException e) {
			Logger.getLogger(gst_rec_scene.class.getName()).log(Level.SEVERE,null,e);
			e.printStackTrace();		
		}
		tvrec.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {	        
		    	Recettes selected = tvrec.getSelectionModel().getSelectedItem();
		        txtcr.setText(selected.getCodeRecette().toString());	
		        txtca.setText(selected.getCodeAdherent().toString());
		        txtmp.setValue(selected.getModePaiement().toString());
		        txtmon.setText(selected.getMontant().toString());
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        dtpec.setValue(LocalDate.parse(selected.getDateEcheance().toString(),formatter));

		};
	});
	    tvrec.getSelectionModel().selectFirst();

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		txtmp.getItems().addAll(MP);
		refresh();
	}
	public void disablecontrols() {
		   txtcr.setDisable(true);
		     txtca.setDisable(true);
		     txtmp.setDisable(true);
		     txtmon.setDisable(true);
		     dtpec.setDisable(true);
	}
	public void enablecontrols() {
		   txtcr.setDisable(false);
		     txtca.setDisable(false);
		     txtmp.setDisable(false);
		     txtmon.setDisable(false);
		     dtpec.setDisable(false);
	}
	public void btnnvclick(ActionEvent e) throws ClassCastException, SQLException {
		if(btnnv.getText().equals("Nouveau")) {
		 enablecontrols();
	     txtcr.setText(null);
	     txtca.setText(null);
	     txtmp.setValue(null);
	     txtmon.setText(null);
	     dtpec.setValue(null);
	     btnnv.setText("Confirmer");
	     btnmod.setText("Annuler");
	     btnsup.setVisible(false);
	     return;
	     }else
	     {if(txtcr.getText()==""||txtca.getText()==""||txtmp.getValue()== null||txtmon.getText()== null||dtpec.getValue()==  null){
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
	  	    tvrec.getSelectionModel().selectNext();
	  	    return;
			}
			if(btnmod.getText().equals("Modifier")) {
		          btnmod.setText("Confirmer");
		          btnsup.setText("Annuler");
		          btnnv.setVisible(false);
		          enablecontrols();
		          txtcr.setDisable(true);
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
		
		public void insert() { 
			try {
				DBConnection connectnow = new DBConnection();
				Connection con = connectnow.sportclubcon();
      String cmd ="INSERT INTO Recettes (CodeRecette,CodeAdherent,ModePaiement,Montant,DateEcheance) VALUES ('"+txtcr.getText()+"','"+txtca.getText()+"','"+txtmp.getValue()+"','"+txtmon.getText()+"','"+dtpec.getValue().toString()+"')";
				Statement stm =con.createStatement();
				stm.executeUpdate(cmd);
				refresh();
			} catch (SQLException e) {
				if (e.getErrorCode() == 1062)
			    {
				Alert alert = new Alert(AlertType.WARNING);
			      alert.setTitle("Error");
			      alert.setHeaderText("Le Code Recette existe déja");
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
				String cmd ="UPDATE Recettes SET ModePaiement ='"+txtmp.getValue()+"',Montant='"+txtmon.getText()+"',DateEcheance='"+dtpec.getValue()+"' WHERE CodeRecette ='"+txtcr.getText()+"'";
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
	        String cmd ="DELETE FROM Recettes WHERE CodeRecette ='"+ txtcr.getText() +"'";
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
		  	    tvrec.getSelectionModel().selectFirst();
		  	  return;
			}else { 
				Alert alert = new Alert(AlertType.CONFIRMATION);
		      alert.setTitle("Delete Recette");
		      alert.setHeaderText("Etes-vous sûr de vouloir supprimer cette recette?");

		      Optional<ButtonType> option = alert.showAndWait();
		      if (option.get() == ButtonType.OK) {
					delete();
					return;
		      } 
			
		   }
		}
		
		public void btngenclick(ActionEvent e) throws DocumentException, MalformedURLException, IOException, SQLException {
			getInfo();
			Document doc = new Document();
			PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\Nabstie\\Downloads\\Facture.pdf"));
			doc.open();
			Image img = Image.getInstance("C:\\Users\\Nabstie\\eclipse-workspace\\SportClub\\src\\application\\SPheader.png");
			img.scaleAbsolute(600,92);
			img.setAlignment(Image.ALIGN_CENTER);
			doc.add(img);
			
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph("Vos Informations:"));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph(" "));
			
			
			doc.add(new Paragraph("Nom:   "+ Nom));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph("Prenom:   "+ Prenom));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph("Adresse:   "+ Adresse));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph("Tele:   "+ Tele));
			doc.add(new Paragraph(" "));
			
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			
			PdfPCell cell;
			
			cell = new PdfPCell (new Phrase("Code Recette",FontFactory.getFont("Comic Sans MS",12)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.GRAY);
			table.addCell(cell);
			cell = new PdfPCell (new Phrase("Code Adherent",FontFactory.getFont("Comic Sans MS",12)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.GRAY);
			table.addCell(cell);
			cell = new PdfPCell (new Phrase("Mode de Paiement",FontFactory.getFont("Comic Sans MS",11)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.GRAY);
			table.addCell(cell);
			cell = new PdfPCell (new Phrase("Montant",FontFactory.getFont("Comic Sans MS",12)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.GRAY);
			table.addCell(cell);
			cell = new PdfPCell (new Phrase("Date d'echeance",FontFactory.getFont("Comic Sans MS",12)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.GRAY);
			table.addCell(cell);
			
	    	Recettes selected = tvrec.getSelectionModel().getSelectedItem();
	    	String CR = selected.getCodeRecette().toString();	
	        String CA = selected.getCodeAdherent().toString();
	        String MP =selected.getModePaiement().toString();
	        String MT =selected.getMontant().toString();
	        String DT =selected.getDateEcheance().toString();
	        
	        cell = new PdfPCell (new Phrase(CR,FontFactory.getFont("Arial",9)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell (new Phrase(CA,FontFactory.getFont("Arial",9)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell (new Phrase(MP,FontFactory.getFont("Arial",9)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell (new Phrase(MT,FontFactory.getFont("Arial",9)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell (new Phrase(DT,FontFactory.getFont("Arial",9)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			doc.add(new Paragraph(" "));
			doc.add(new Paragraph("Votre Recette:"));
			doc.add(new Paragraph(" "));
			doc.add(table);
			
			
			
			doc.close();
			Desktop.getDesktop().open(new File("C:\\Users\\Nabstie\\Downloads\\Facture.pdf"));
		}
		
		 private String Nom;
		 private String Prenom;
		 private String Adresse;
		 private String Tele;
		 
		public void getInfo() throws SQLException {
			Recettes selected = tvrec.getSelectionModel().getSelectedItem();
	        String adh = selected.getCodeAdherent().toString();
			DBConnection connectnow = new DBConnection();
			Connection con = connectnow.sportclubcon();
			String cmd = "Select Nom,Prenom,Adresse,Tele FROM Adherents WHERE CodeAdherent ='"+adh+"'";
				Statement stm =con.createStatement();
		    	ResultSet rs = stm.executeQuery(cmd);
		    	while(rs.next()) {
		    		Nom = rs.getString("Nom");
		    		Prenom = rs.getString("Prenom");
		    		Adresse = rs.getString("Adresse");
		    		Tele = rs.getString("Tele");
		       }

		}
}


