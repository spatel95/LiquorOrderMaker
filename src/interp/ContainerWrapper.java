package interp;

import java.util.List;

import analyze.Container;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.knaw.dans.common.dbflib.Record;

public class ContainerWrapper {

	private SimpleBooleanProperty selected;
	private final SimpleStringProperty barcode;
	private final SimpleStringProperty brand;
	private final SimpleStringProperty description;
	private final SimpleStringProperty size;
	private final SimpleIntegerProperty qty;
	private final SimpleIntegerProperty mtd;
	private final SimpleIntegerProperty ytd;

	public ContainerWrapper(Container c) {
		// super(c.getRecord());
		barcode = new SimpleStringProperty(c.getBarcode().trim());
		brand = new SimpleStringProperty(c.getBrand().trim());
		description = new SimpleStringProperty(c.getDescription().trim());
		size = new SimpleStringProperty(c.getSize().trim());
		qty = new SimpleIntegerProperty(c.getData("QTY_ON_HND").intValue());
		mtd = new SimpleIntegerProperty(c.getData("MTD").intValue());
		ytd = new SimpleIntegerProperty(c.getData("YTD").intValue());
		selected = new SimpleBooleanProperty(false);

	}

	public String getBarcode() {
		return barcode.get();
	}

	public String getBrand() {
		return brand.get();
	}

	public String getDescription() {
		return description.get();
	}

	public String getSize() {
		return size.get();
	}

	public int getQTY() {
		return qty.get();
	}

	public int getMTD() {
		return mtd.get();
	}
	public int getYTD(){
		return ytd.get();
	}
	
	public boolean isSelected(){
		return selected.get();
	}
	
	public void toggleSelected(){
		if(selected.get()){
			selected.set(false);
		}else{
			selected.set(true);
		}
	} 	
	
	public void setSelected(boolean bol){
		this.selected.set(bol);
	}
	
	public Boolean getSelected(){
		return selected.get();
	}
	
	public BooleanProperty selectedProperty(){
		return selected;
	}

	public static ObservableList<ContainerWrapper> containerWrapperList(List<Container> l) {
		ObservableList<ContainerWrapper> list = FXCollections.observableArrayList();

		for (Container c : l) {
			list.add(new ContainerWrapper(c));
		}

		return list;
	}

	@Override
	public String toString() {
		return "Barcode -> " + barcode + "\n" + "Brand -> " + brand + "\n" + "Description -> " + description + "\n"
				+ "Size -> " + size + "\n" + "QTY -> " + qty + " " + "MTD -> " + mtd + " " + "YTD -> " + ytd
				+ "\n" + "<----------------------------------->";
	}

	public static void printList(List<ContainerWrapper> l) {
		if (l.isEmpty()) {
			System.out.println("LIST IS EMPTY!");
		}
		for (ContainerWrapper c : l) {
			System.out.println(c);
		}
	}

}
