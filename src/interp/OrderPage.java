package interp;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OrderPage  {
	public static void display(){
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		
		stage.setTitle("Order Maker");
		
		HBox buttonLayout = new HBox();
		buttonLayout.setAlignment(Pos.CENTER);
		buttonLayout.setSpacing(15);
		
		Button exit = new Button();
		exit.setText("Close");
		exit.setAlignment(Pos.CENTER_RIGHT);
		exit.setOnMouseClicked(e->{stage.close();});
		
		Button makeOrder = new Button();
		makeOrder.setText("Make Order");
		
		
				
		
	}
}
