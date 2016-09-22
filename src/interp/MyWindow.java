package interp;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import analyze.Container;
import javafx.application.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.*;
import javafx.util.Callback;


public class MyWindow extends Application {
	private MyWindowController controller;
	private Stage mainStage;
	private String selectedBrand;
	private ArrayList<Container> selectedItemsList;
	private ArrayList<String> brandList;
	
	private HashMap<String, ArrayList<Container>> checkedMap;

	private Scene openScene, mainScene;

	public static void main(String[] args) {
		launch(args);
	}

	
	// Start executes the GUI
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		mainStage.setTitle("MyOrderMaker");
		
		//used to store selected brands and its associated items that are checked
		checkedMap = new HashMap<>();
		
		//Main Window that is the "screen"
		mainStage.setScene(fileSelectScene());
		mainStage.show();
	}

	/* The Left side of the BorderPane used as the main scene will be a list of all the brands
	 * The Right side is left yet to be undecided
	 * The Center is there the table stored
	 * the Bottom is where all of the buttons for executions as kept 
	 */
	private Scene mainScene() {
		//Main Layout of the program
		BorderPane layout = new BorderPane();
		//Wait Do I even need this list??
		brandList = controller.getBrandList();
		
		//The list is what is "shown" by the list view, IN THE SAME ORDER
		//THIS MEANS USE THE INDEX OF THIS LIST TO ACCESS ANY "BRAND" FROM THE LISTVIEW
		ObservableList<String> list = FXCollections.observableArrayList(brandList);
		ListView<String> listView = new ListView<>();
		
		//Lets me Selects more than one items from the list 
		listView.getSelectionModel().selectionModeProperty().set(SelectionMode.MULTIPLE);
		
		//This callback handles adding and removing a WHOLE BRAND AND ITS ASSOCIETED ITEMS FROM THE MAP
		//It also adds the functionality of a "checkbox" on the left of the brand name
		Callback<String,ObservableValue<Boolean>> cb = new Callback<String, ObservableValue<Boolean>>() {

			@Override
			public ObservableValue<Boolean> call(String param) {
				
				BooleanProperty checkbox = new SimpleBooleanProperty();
				
				//handler adds and removes key/val pairing
				//NOTE: the listView selection handler is also ran right after this
				checkbox.addListener((obe,lastBol,newBol)->{
					
					listView.getSelectionModel().select(param);
					if(newBol){
						checkedMap.put(param, new ArrayList<>());
						listView.getSelectionModel().select(param);
					}else{
						
						checkedMap.remove(param);
						listView.getSelectionModel().clearSelection(listView.getSelectionModel().getSelectedIndex());
					}
					//Debug
//					System.out.println(param+" ->> "+checkedMap.get(param));
				});
				
				return checkbox;
			}
		};
		
		//Setting the CallBack to the cellFactory
		listView.setCellFactory(CheckBoxListCell.forListView(cb));
		listView.setItems(list);
		
		//What happens when you click on a list cell
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			//sets the selected brand to selectedBrand, and will be used for all of the functionality  for the table
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				//selects the brand
				listView.getSelectionModel().select(newValue);
				selectedBrand = newValue;
				//changes the layout of the center to selected brand  
				layout.setCenter(infoTable(newValue));
			}
		});
		
		listView.setOnMouseClicked(e->{
			System.out.println(e.getSource());
		});
				
		layout.setLeft(listView);
		layout.setCenter(new VBox());
		layout.setBottom(makeOrderController());

		mainScene = new Scene(layout, 900, 550);
		mainScene.getStylesheets().add(this.getClass().getResource("listView.css").toExternalForm());

		return mainScene;
	}

	/*
	 * This Table Has Barcode, Brand, Description, Size, Quantity On Hand, MTD and
	 * YTD
	 */

	private Node infoTable(String s) {

		selectedItemsList = controller.getAll(s);
		ObservableList<Container> list = FXCollections.observableArrayList(selectedItemsList);

		CheckBox selectAll = new CheckBox();
		
		// ContainerWrapper.printList(list);
		TableView<Container> table = new TableView<>();
		table.setItems(list);
		
		
		
		TableColumn<Container, Boolean> checkBoxCol = new TableColumn<>();
		checkBoxCol.setCellValueFactory(new PropertyValueFactory<Container, Boolean>("selected"));
		checkBoxCol.setCellFactory(CheckBoxTableCell.forTableColumn(new Callback<Integer,ObservableValue<Boolean>>() {

			@Override
			public ObservableValue<Boolean> call(Integer param) {
				if(list.get(param).isSelected()){
					checkedMap.get(selectedBrand).add(list.get(param));
				}else{
					if(checkedMap.containsKey(selectedBrand)){
						checkedMap.get(selectedBrand).remove(list.get(param));
					}	
				}
				
//				ArrayList<Container> l = checkedMap.get(selectedBrand);
//				
//				for (Container c : l) {
//					System.out.println(c);
//				}
				return list.get(param).selectedProperty();
			}
		}));

		checkBoxCol.setEditable(true);
		checkBoxCol.setGraphic(selectAll);

		selectAll.setOnAction(e->{
			for (Container c : list) {		
				c.setSelected(((CheckBox)e.getSource()).isSelected());
				if(((CheckBox)e.getSource()).isSelected()){
					checkedMap.get(c.getBrand()).add(c);
				}else{
					checkedMap.get(c.getBrand()).remove(c);
				}
			}
		});

		
		TableColumn<Container, String> barcode = new TableColumn<>("Barcode");
		barcode.setCellValueFactory(new PropertyValueFactory<>("Barcode"));

		TableColumn<Container, String> brand = new TableColumn<>("Brand");
		brand.setCellValueFactory(new PropertyValueFactory<>("Brand"));

		TableColumn<Container, String> description = new TableColumn<>("Description");
		description.setCellValueFactory(new PropertyValueFactory<>("Description"));

		TableColumn<Container, String> size = new TableColumn<>("Size");
		size.setCellValueFactory(new PropertyValueFactory<>("Size"));

		TableColumn<Container, String> qty = new TableColumn<>("QTY");
		qty.setCellValueFactory(new PropertyValueFactory<>("qty"));

		TableColumn<Container, String> mtd = new TableColumn<>("MTD");
		mtd.setCellValueFactory(new PropertyValueFactory<>("MTD"));

		TableColumn<Container, String> ytd = new TableColumn<>("YTD");
		ytd.setCellValueFactory(new PropertyValueFactory<>("YTD"));

		table.getColumns().setAll(checkBoxCol,barcode, brand, description, size, qty, mtd, ytd);
		table.setEditable(true);

		return table;
	}

	private Node makeOrderController() {

		HBox hButton = new HBox();
		Button makeOrderButton = new Button("Simple Order List");
		makeOrderButton.setOnMouseClicked(e->{
			Integer months = GetInfo.getMonths();
			SimpleOrder.display(checkedMap,controller,months);
		});

		makeOrderButton.setMinWidth(200);
		
		Button smartOrderButton = new Button("Smart Order List");
		smartOrderButton.setMinWidth(200);
		
		Button moreInfo = new Button("Seleced Info");
		moreInfo.setMinWidth(200);
		
		moreInfo.setOnMouseClicked(e->{
			int months = GetInfo.getMonths();
			SelectedInfoWindow.display(checkedMap,controller, months);
		});
		
		checkedMap.keySet();
		Button stat = new Button("analyze info");
		stat.setMinWidth(200);
		stat.setOnMouseClicked(e->System.out.println(controller.analyzeData(new ArrayList<>(checkedMap.keySet()), 6)));

		hButton.setSpacing(20);
		hButton.setAlignment(Pos.CENTER);
		hButton.getChildren().setAll(stat,moreInfo,makeOrderButton, smartOrderButton);

		return hButton;
	}


	private Scene fileSelectScene() {

		//The text that appears where the file was selected. DO I REALLY NEED IT?? MAYBE A BETTER IMPLEMENTATION??
		TextField fileField = new TextField("DBF File");

		// Adds the functionally of the action of choosing and retrieving a file.
		final FileChooser fileChooser = new FileChooser();

		//button that chooses executes the file chooser
		Button openButton = new Button("Open File");
		openButton.setMinWidth(200);
		openButton.setOnAction((e) -> {
			//NOTE: THIS BIT OF CODE IS ONLY EXECUTED WHEN THE BUTTON IS PRESSED
			
			//indicator is there to make sure that file chooser executed correctly and the file chosen is valid
			boolean indicator = false;
			File file = fileChooser.showOpenDialog(mainStage);
			try {
				fileField.setText(file.getName());
				controller = new MyWindowController(file);
				indicator = true;
			} catch (IllegalArgumentException | NullPointerException exp) {
				ErrorBox.display("Error", "Please Select a File!");
			}
			
			// if the file was open correctly then the scene is changed to mainScene
			if (indicator) {
				mainStage.setWidth(900);
				mainStage.setHeight(450);
				mainStage.setScene(mainScene());
			}
		});

		//horizantal "row" where the button is located
		HBox hButton = new HBox();
		hButton.setSpacing(15);
		hButton.getChildren().addAll(openButton);

		//the gris will hold both the text field and the button
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(40);
		// the two int act as coordinates 
		grid.add(hButton, 1, 3);
		grid.add(fileField, 1, 2);
		
		//for centering then everything is aligned inside the grid "cell"
		grid.setAlignment(Pos.CENTER);
		grid.setAlignment(Pos.TOP_CENTER);

		openScene = new Scene(grid, 400, 200);
		return openScene;
	}
	
	@SuppressWarnings("unused")
	private ArrayList<ContainerWrapper> getSelectedList(ObservableList<ContainerWrapper> list){
		ArrayList<ContainerWrapper> l = new ArrayList<>();
		
		for (ContainerWrapper c : list) {
			if(c.isSelected()){
				l.add(c);
			}
		}
		
		for (ContainerWrapper c : l) {
			System.out.println(c.getBrand()+" -> "+c.getDescription()+" ->>> "+c.getSelected());
		}
		
		return l;
		
	}
	
	public void printChechedMap(){
		for (String string : checkedMap.keySet()) {
			System.out.println(string);
		}
	}

}
