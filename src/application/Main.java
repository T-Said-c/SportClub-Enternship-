package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;



public class Main extends Application {
	@Override
	public void start(Stage Stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));

			Scene scene = new Scene(root);
			Image icon = new Image("sportclub.png") ;
            Stage.resizableProperty().setValue(false);
			Stage.setTitle("Golden Club Manager");
			Stage.getIcons().add(icon);
			Stage.setScene(scene);
			Stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
