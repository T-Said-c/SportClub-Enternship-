package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class welcome_scene implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	private ImageView imgv;
	
	public void load() throws IOException {
		  root = FXMLLoader.load(getClass().getResource("login.fxml"));
		  stage = (Stage)imgv.getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.centerOnScreen();
		  stage.show();	
		  return;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	        @Override
	        public void run() {
	            Platform.runLater(() -> {
	                try {
						load();
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
	            });	return;	         

	        }
          
	    }, 3000 , 5000 );return;
	    
		      
		        
		
	}
	

}
