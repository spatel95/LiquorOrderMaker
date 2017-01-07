package analyze;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import nl.knaw.dans.common.dbflib.*;

public class Container implements Comparable<Container> {
//	private LinkedHashMap<String, String> id;
//	private LinkedHashMap<String, Number> data;
	private Record record;
//	private String brand;
//	private String description;
//	private String size;
//	private String barcode;
	
	private SimpleBooleanProperty selected;
	private SimpleStringProperty barcode;
	private SimpleStringProperty brand;
	private SimpleStringProperty description;
	private SimpleStringProperty size;
	private SimpleIntegerProperty qty;
	private SimpleIntegerProperty mtd;
	private SimpleIntegerProperty ytd;
	private int[] monthSale;

	public Container(Record r) {
		record = r;
//		id = new LinkedHashMap<>();
//		data = new LinkedHashMap<>();
		
		monthSale= new int[13];
		brand = new SimpleStringProperty(r.getStringValue("BRAND").trim());
		description = new SimpleStringProperty(r.getStringValue("DESCRIP").trim());
		size = new SimpleStringProperty(r.getStringValue("SIZE").trim());
		barcode = new SimpleStringProperty(r.getStringValue("BARCODE").trim());
		qty = new SimpleIntegerProperty(r.getNumberValue("QTY_ON_HND").intValue());
		mtd = new SimpleIntegerProperty(r.getNumberValue("MTD").intValue());
		ytd = new SimpleIntegerProperty(r.getNumberValue("YTD").intValue());
		selected = new SimpleBooleanProperty(false);

//		id.put("BARCODE", r.getStringValue("BARCODE"));
//		id.put("BRAND", r.getStringValue("BRAND"));
//		id.put("DESCRIP", r.getStringValue("DESCRIP"));
//		id.put("TYPE", r.getStringValue("TYPE"));
//		id.put("SIZE", r.getStringValue("SIZE"));
//		id.put("BIN", r.getStringValue("BIN"));
		
//		System.out.println(brand+""+ description+ ""+size+""+barcode+ ""+ r.getNumberValue("STD_QTY").intValue());
		
//		data.put("QTY_CASE", r.getNumberValue("QTY_CASE"));
//		data.put("STD_QTY", r.getNumberValue("STD_QTY"));
//		data.put("PRICE", r.getNumberValue("PRICE"));
//		data.put("COST", r.getNumberValue("COST"));
//		data.put("QTY_ON_HND", r.getNumberValue("QTY_ON_HND"));
//		data.put("CASE_PRICE", r.getNumberValue("CASE_PRICE"));
//		data.put("MTD", r.getNumberValue("MTD"));
//		data.put("YTD", r.getNumberValue("YTD"));
//
//		data.put("PRIORY", r.getNumberValue("PRIORY"));
//		data.put("SALES", r.getNumberValue("SALES"));
		
		monthSale[1] = r.getNumberValue("FIRST").intValue();
		monthSale[2] = r.getNumberValue("SECON").intValue();
		monthSale[3] = r.getNumberValue("THIRD").intValue();
		monthSale[4] = r.getNumberValue("FOURT").intValue();
		monthSale[5] = r.getNumberValue("FIFTH").intValue();
		monthSale[6] = r.getNumberValue("SIXTH").intValue();
		monthSale[7] = r.getNumberValue("SEVEN").intValue();
		monthSale[8] = r.getNumberValue("EIGHT").intValue();
		monthSale[9] = r.getNumberValue("NINTH").intValue();
		monthSale[10] = r.getNumberValue("TENTH").intValue();
		monthSale[11] = r.getNumberValue("ELEVE").intValue();
		monthSale[12] = r.getNumberValue("TWELV").intValue();
	}
	
	public int getSaleAtMonth(int i){
		if(i<1){
			System.out.println("Invalid Month");
			return -1;
		}else{
			return monthSale[i];
		}
	}
	
	
	public String getIdValue(String s){
		return record.getStringValue(s);
	}
	
	public SimpleIntegerProperty getQTY(){
		return qty;
	}
	
	public int getQTYValue(){
		return qty.get();
	}
	
	public Number getData(String s) {
		return record.getNumberValue(s);
	}
	
	public String getBin(){
		return record.getStringValue("BIN");
	}

	public String getBarcode() {
		return barcode.getValue();
	}
	
	public String getType() {
		return record.getStringValue("TYPE");
	}

	public String getBrand() {
		return brand.getValue();
	}

	public String getDescription() {
		return description.getValue();
	}

	public String getSize() {
		return size.getValue();
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

	public Record getRecord() {
		return record;
	}

	public String toString() {
		return "Brand--" + brand + " Description--" + description + "\t Size--" + size + "\t STD_QTY--"
				+ this.getData("STD_QTY") + "\t YTD--" + this.getData("YTD");
	}

	public int pastQtySold(int months) {
		int total = 0;
		for (int i = 1; i <= months; i++) {
			total += this.getSaleAtMonth(i);
		}
		return total;
	}

	@Override
	public int compareTo(Container o) {
		int compare = this.getBrand().compareTo(o.getBrand());

		if (compare > 0 || compare < 0) {
			return compare;
		} else {
			compare = this.getDescription().compareTo(o.getDescription());
			if (compare > 0 || compare < 0) {
				return compare;
			} else {
				return this.getSize().compareTo(o.getSize());

			}

		}

	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof Container)) {
			return false;
		}

		else {
			Container c = (Container) o;

//			if (this.compareTo(c) == 0) {
//				return true;
//			}
//
//			return false;

			return this.compareTo(c) == 0;

		}
	
	}
	
	

}
