package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class stat_scene implements Initializable {
	@FXML
	private LineChart lnch;
	
	@FXML
	private ChoiceBox<String> cb;
	
	private String[] fil = {"Annee","Mois"};
	
	public void ParAnnee() throws SQLException {
		XYChart.Series series = new XYChart.Series();
		series.setName("sales");
		
		DBConnection connectnow = new DBConnection();
		Connection con = connectnow.sportclubcon();
		String cmd = "Select SUM(Nombre),Annee FROM Sales GROUP BY Annee";
			Statement stm =con.createStatement();
	    	ResultSet rs = stm.executeQuery(cmd);
	    	while(rs.next()) {
	    		series.getData().add(new XYChart.Data<>(rs.getString("Annee"), rs.getDouble("SUM(Nombre)")));}
	    	lnch.getData().add(series);
       
	}
	    	
			public void ParMois() throws SQLException {
	    		XYChart.Series series = new XYChart.Series();
	    		series.setName("Sales");
	    		
	    		DBConnection connectnow = new DBConnection();
	    		Connection con = connectnow.sportclubcon();
	    		String cmd = "Select Nombre,Annee,Mois FROM Sales";
	    			Statement stm =con.createStatement();
	    	    	ResultSet rs = stm.executeQuery(cmd);
	    	    	while(rs.next()) {
	    	    		series.getData().add(new XYChart.Data<>(rs.getString("Mois")+" "+rs.getString("Annee"), rs.getDouble("Nombre")));
	    	       }
	
	
	lnch.getData().add(series);
	}
	    	
	    	public void changefil(ActionEvent e) throws SQLException {
	    	      lnch.getData().clear();
	    		if(cb.getValue() =="Annee") {
	    		ParAnnee();
	    	    	}else {
	    	    		ParMois();
	    	    	}
	    	}	
               
	    	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			cb.getItems().addAll(fil);
			cb.setValue("Mois");
			cb.setOnAction(arg01 -> {
				try {
					changefil(arg01);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			});
			try {
				ParMois();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

		
}
