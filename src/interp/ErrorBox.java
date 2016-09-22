package interp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorBox {

	
	
	public static void display(String title,String message){
		
		Label lbl = new Label();
		lbl.setText(message);
//		System.out.println(lbl.getScaleX());
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinHeight(100);
		stage.setMinWidth(lbl.getScaleX()+30.0);
		
		Button button = new Button("OK");
		button.setOnAction(e->stage.close());
		
		VBox layout = new VBox(15);
		
		layout.getChildren().addAll(lbl,button);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
//		scene.
		
		stage.setScene(scene);
		
		stage.showAndWait();
	}
	
	
	
}
