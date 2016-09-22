package interp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.event.TableColumnModelListener;

import analyze.Analyzer;
import analyze.Container;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SelectedInfoWindow {

	//Need to add coloring green if went up and red if down from the previous months
	@SuppressWarnings("unchecked")
	public static void display(Map<String, ArrayList<Container>> map,MyWindowController controller, int months){
		
		Stage stage = new Stage();
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Detailed Information");
		
		stage.setHeight(500);
		stage.setWidth(900);
		
		
		VBox layout = new VBox();
		TableView<Container> table = new TableView<>();
		
		TableColumn<Container, String> brand = new TableColumn<>("Brand");
		brand.setCellValueFactory(new PropertyValueFactory<>("Brand"));

		TableColumn<Container, String> description = new TableColumn<>("Description");
		description.setCellValueFactory(new PropertyValueFactory<>("Description"));

		TableColumn<Container, String> size = new TableColumn<>("Size");
		size.setCellValueFactory(new PropertyValueFactory<>("Size"));
		
		table.getColumns().addAll(brand,description,size);
		
		for (int i = 1; i <= months; i++) {
			final int j = i;
			TableColumn<Container, Integer> col = new TableColumn<Container, Integer>("Month "+i);
			col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Container,Integer>, ObservableValue<Integer>>() {

				@Override
				public ObservableValue<Integer> call(CellDataFeatures<Container, Integer> param) {
					return new ReadOnlyObjectWrapper<Integer>(param.getValue().getSaleAtMonth(j));
				}
			});
			
			table.getColumns().add(col);
		}
		
		TableColumn<Container,Double> average = new TableColumn<Container, Double>("Average");
		average.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Container,Double>, ObservableValue<Double>>() {

			@Override
			public ObservableValue<Double> call(CellDataFeatures<Container, Double> param) {
				return new ReadOnlyObjectWrapper<Double>(Double.parseDouble(df.format(controller.getAverage(param.getValue().getBarcode(), months))));
			}
			
			
		});
		table.getColumns().add(average);
		
		TableColumn<Container,Double> stdDiv = new TableColumn<Container, Double>("STD DIV");
		stdDiv.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Container,Double>, ObservableValue<Double>>() {

			@Override
			public ObservableValue<Double> call(CellDataFeatures<Container, Double> param) {
				return new ReadOnlyObjectWrapper<>(Double.parseDouble(df.format(controller.getStdDiv(param.getValue().getBarcode(), months))));
			}
			
			
		});
		
		table.getColumns().add(stdDiv);
		
		
		table.setItems(getData(map));
		Scene scene = new Scene(table);
		stage.setScene(scene);
		stage.showAndWait();
		
	}
	
	
	private static ObservableList<Container> getData(Map<String, ArrayList<Container>> map){
		ObservableList<Container> list = FXCollections.observableArrayList();
		for (Entry<String, ArrayList<Container>> e : map.entrySet()) {
			//list.add(e.getValue());
			for (Container c : e.getValue()) {
				list.add(c);
			}
		}
		return list;
	}
	
	
	

}
