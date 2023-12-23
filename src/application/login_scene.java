package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class login_scene {
	@FXML
	private Button btlog;
	@FXML
	private TextField txtemail;
	@FXML
	private PasswordField txtmdp;
	@FXML
	private TextField txtmdpshown;
	@FXML
	private AnchorPane loginanch;
	@FXML
	private Label lberror;
	@FXML
	private CheckBox cbshow;
	private Stage stage;
	private Scene scene;
	private Parent root;
	public String pr;
	

	public void getpermission() throws SQLException {
		DBConnection connectnow = new DBConnection();
		Connection con = connectnow.sportclubcon();
		String cmd = "Select PermissionLevel from utilisateurs where Email = '"+ txtemail.getText()+"'";
			Statement stm =con.createStatement();
	    	ResultSet rs = stm.executeQuery(cmd);
	    	while(rs.next()) {
	    		int i =rs.getInt("PermissionLevel");
	    		pr = String.valueOf(i);
	    	}
	    	
	}

    public void loginclick(ActionEvent e) {
    	if((txtemail.getText().isBlank() == false && txtmdp.getText().isBlank() == false) || (txtemail.getText().isBlank() == false && txtmdpshown.getText().isBlank() == false) ) {
    		DBConnection connectnow = new DBConnection();
    		Connection con = connectnow.sportclubcon();
    		String cmd;
    		if(cbshow.isSelected()) {
    		cmd = "Select count(1) From utilisateurs where Email = '" + txtemail.getText() + "' and MDP = '" + txtmdpshown.getText() + "'";
    		}
    		else {
        	cmd = "Select count(1) From utilisateurs where Email = '" + txtemail.getText() + "' and MDP = '" + txtmdp.getText() + "'";
    		}
    	    try {
    	    	Statement stm =con.createStatement();
    	    	ResultSet rs = stm.executeQuery(cmd);
    	    	while(rs.next()) {
    	    		if(rs.getInt(1) == 1) {
    	    			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
    	    			root = loader.load();
    	    			  MainMenu_scene mainmenu =loader.getController();
    	    			  getpermission();
    	    			  mainmenu.displayperm(pr);
    	    			  stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	    			  scene = new Scene(root);
    	    			  stage.setScene(scene);
    	    			  stage.centerOnScreen();
    	    			  stage.show();
    	    		}
    	    		else {
    	    			txtmdp.setText("");
    	    			txtmdpshown.setText("");
    	    			lberror.setText("L'email ou le mdp est incorrecte!");
    	    		}
    	    	}
    	    }catch(Exception e1) {
    	    	e1.printStackTrace();
    	    }
    	}
    	else {
    		lberror.setText("Veuillez entrer l'email et le mdp!");
    	}
    	
    }
    public void changecolor(ActionEvent e) {
    	btlog.styleProperty().set("-fx-background-color: #545353");
    }
    
   public void showmdp(ActionEvent e) {
	   if(cbshow.isSelected()) {
		   txtmdpshown.setText(txtmdp.getText());
		   txtmdpshown.setVisible(true);
		   txtmdp.setVisible(false);
		   return;
	   }
	   txtmdp.setText(txtmdpshown.getText());
	   txtmdp.setVisible(true);
	   txtmdpshown.setVisible(false);	   
   }
    

}
