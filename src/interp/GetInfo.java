package interp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GetInfo {
	
	
	public static Integer getMonths(){
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Time Period");
		
		stage.setHeight(300);
		stage.setWidth(400);
		
		GridPane layout = new GridPane();
		
		TextField numberField = new TextField();
		
		Button btn = new Button("Enter Number");
		
		btn.setOnMouseClicked(e->{
			stage.close();
			
		});
		
		layout.setVgap(20);
		layout.setHgap(40);
		
		layout.add(numberField, 1, 3);
		layout.add(btn, 1, 2);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout,400,200);
		
		stage.setScene(scene);
		stage.showAndWait();
		
		return Integer.parseInt(numberField.getText());
		
		
		
		
		
		
	}
	
}
