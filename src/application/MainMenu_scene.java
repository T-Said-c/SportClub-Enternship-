package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenu_scene {

	@FXML
	private AnchorPane changedanch;
	@FXML
	private Label lbpl;
	@FXML
	private Button btndep;
	@FXML
	private Button btnser;
	@FXML
	private Button btnper;
	@FXML
	private Button btnpv;
	@FXML
	private Button btnuti;
	@FXML
	private Button btnsta;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void logoutclick(ActionEvent e) throws IOException {
		  root = FXMLLoader.load(getClass().getResource("login.fxml"));
		  stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.centerOnScreen();
		  stage.show();
	}
	public void displayperm(String pr) {
		lbpl.setText(pr);
		if(pr.equals("1")) {
			btndep.setVisible(false);
			btnser.setVisible(false);
			btnper.setVisible(false);
			btnpv.setVisible(false);
			btnuti.setVisible(false);
			btnsta.setVisible(false);
		}if(pr.equals("2")) {		
			btnuti.setVisible(false);
			btnsta.setVisible(false);		
		}
	}
	
	private void setDynamicPane(AnchorPane changedanch){
	      this.changedanch.getChildren().clear();
	      this.changedanch.getChildren().add(changedanch);
	}
	public void gst_adhclick(ActionEvent e) throws IOException {
			setDynamicPane(FXMLLoader.load(getClass().getResource("gst_adh.fxml")));
	}
	public void gst_aboclick(ActionEvent e) throws IOException {
		setDynamicPane(FXMLLoader.load(getClass().getResource("gst_abon.fxml")));
}
	public void gst_recclick(ActionEvent e) throws IOException {
		setDynamicPane(FXMLLoader.load(getClass().getResource("gst_rec.fxml")));
}
	public void gst_serclick(ActionEvent e) throws IOException {
		setDynamicPane(FXMLLoader.load(getClass().getResource("gst_ser.fxml")));
}
	public void gst_depclick(ActionEvent e) throws IOException {
		setDynamicPane(FXMLLoader.load(getClass().getResource("gst_dep.fxml")));
}
	public void gst_uticlick(ActionEvent e) throws IOException {
		setDynamicPane(FXMLLoader.load(getClass().getResource("gst_uti.fxml")));
}
	public void gst_perclick(ActionEvent e) throws IOException {
		setDynamicPane(FXMLLoader.load(getClass().getResource("gst_per.fxml")));
}
	public void gst_resclick(ActionEvent e) throws IOException {
		setDynamicPane(FXMLLoader.load(getClass().getResource("gst_res.fxml")));
}
	public void pnt_vteclick(ActionEvent e) throws IOException {
		setDynamicPane(FXMLLoader.load(getClass().getResource("pnt_vte.fxml")));
}
	
}
