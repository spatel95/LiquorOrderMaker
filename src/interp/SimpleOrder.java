package interp;

import java.util.ArrayList;
import java.util.Map;

import analyze.Container;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SimpleOrder {
	@SuppressWarnings("unchecked")
	public static void display(Map<String, ArrayList<Container>> map,MyWindowController controller,int months){
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Simple Order List");
		
		stage.setHeight(500);
		stage.setWidth(500);
		
		TableView<Container> table = new TableView<>();
		
		TableColumn<Container, String> brand = new TableColumn<>("Brand");
		brand.setCellValueFactory(new PropertyValueFactory<>("Brand"));

		TableColumn<Container, String> description = new TableColumn<>("Description");
		description.setCellValueFactory(new PropertyValueFactory<>("Description"));

		TableColumn<Container, String> size = new TableColumn<>("Size");
		size.setCellValueFactory(new PropertyValueFactory<>("Size"));
		
		
		
		TableColumn<Container, Integer> qty = new TableColumn<>("QTY");
		qty.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Container,Integer>, ObservableValue<Integer>>() {

			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Container, Integer> param) {
				return new ReadOnlyObjectWrapper<Integer>(param.getValue().getQTYValue());
			}
		});
		table.getColumns().addAll(brand,description,size,qty);
		
		TableColumn<Container, Integer> sold = new TableColumn<>("Sold");
		sold.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Container,Integer>, ObservableValue<Integer>>() {
			
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Container, Integer> param) {
				return new ReadOnlyObjectWrapper<>(controller.simpleOrderList(param.getValue().getBarcode(), months).get(1));
			}
		});
		
		TableColumn<Container, Integer> order = new TableColumn<>("To Order");
		order.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Container,Integer>, ObservableValue<Integer>>() {
			
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Container, Integer> param) {
				return new ReadOnlyObjectWrapper<>(controller.simpleOrderList(param.getValue().getBarcode(), months).get(2));
			}
		});
		
		table.getColumns().addAll(sold,order);
		
		ObservableList<Container> list = FXCollections.observableArrayList();
		for (ArrayList<Container> l : map.values()) {
			list.addAll(l);
		}
		
		table.setItems(list);
		
		Scene scene = new Scene(table);
		stage.setScene(scene);
		stage.showAndWait();
		
	}
}
