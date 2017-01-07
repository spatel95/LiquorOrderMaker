package analyze;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import exception.BrandDoesNotExistException;
import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;

public class TableMap {
	private LinkedHashMap<String, ArrayList<String>> address; //Brand Name to Barcode list mapping
	private LinkedHashMap<String, Container> map; // Barcode to Container Mapping

	public TableMap(Table t) throws CorruptedTableException, IOException {
		map = new LinkedHashMap<>();
		address = new LinkedHashMap<>();
		int size = t.getRecordCount();
		Container c;
		String brand;
		String bar;
		System.out.println("SIZE: "+size);
		for (int i = 0; i < size; i++) {
			Record r = t.getRecordAt(i);
			c = new Container(r);
			brand = c.getBrand();
			bar = c.getBarcode();

			if (!address.containsKey(brand)) {
				address.put(brand, new ArrayList<String>());
			}

			address.get(brand).add(bar);
			map.put(bar, c);
		}
	}

	public void printAdress() {
		for (Map.Entry<String, ArrayList<String>> e : address.entrySet()) {
			System.out.println(e.getKey());
			for (String s : e.getValue()) {
				System.out.println(s);
			}
			System.out.println("---------------------");
		}
	}
	
	public String barcodeToDescription(String s){
		return map.get(s).getDescription();
	}
	
	public String barcodeToSize(String s){
		return map.get(s).getSize();
	}

	//This Takes in String Barcode!
	public Container get(final String s) throws BrandDoesNotExistException {
		Container container = map.get(s);

		if (container.equals(null)) {
			throw new BrandDoesNotExistException(s);
		}

		return container;
	}
	
	public ArrayList<String> getBrandList(){
		ArrayList<String> list = new ArrayList<>();
		Map<String, ArrayList<String>> sortedAddress = new TreeMap<>(address);
		
		for (String s : sortedAddress.keySet()) {
			list.add(s);
		}
		
		return list;
		
	}
	
	public ArrayList<String> getBrandList(String brand) throws BrandDoesNotExistException {
//		System.out.println(brand+"\n\n");
//		System.out.println(address.get(brand).toString());
		
		ArrayList<String> list = new ArrayList<>();
		for (String s : address.keySet()) {
			if(s.contains(brand)){
				return address.get(s);
			}
		}
		throw new BrandDoesNotExistException(brand);
	}

	public String toString() {
		String stringTable = new String();
		for (Map.Entry<String, Container> entry : map.entrySet()) {
			stringTable += entry.getValue().toString() + "\n";
		}
		return stringTable;
	}

	public ArrayList<String[]> order(String brand, int numberOfMonth) {
		brand = brand.toUpperCase();
		ArrayList<String[]> orderList = new ArrayList<>();
		Container cont;
		int qty;
		int sold;
		int toOrder;
		ArrayList<String> list;
		for (Entry<String, ArrayList<String>> e : address.entrySet()) {
			if (e.getKey().contains(brand)) {
				list = e.getValue();
				for (String s : list) {
					String[] arr = new String[4];

					cont = map.get(s);
					qty = cont.getData("QTY_ON_HND").intValue();
					sold = cont.pastQtySold(numberOfMonth);
					arr[0] = cont.getBarcode();
					arr[1] = Integer.toString(qty);
					arr[2] = Integer.toString(sold);
					toOrder = sold - qty;

					if (toOrder < 0) {
						toOrder = 0;
					}

					arr[3] = Integer.toString(toOrder);
					orderList.add(arr);
				}
			}
		}

		return orderList;
	}
	


	public ArrayList<String[]> linearizedOrder(String brand, int numberOfMonth) {

		brand = brand.toUpperCase();
		ArrayList<Integer> linerMovementArray = new ArrayList<>();

		int total;
		
		return null;

	}

	/*
	 * How to analize 1) find the brand 2) go though each item -start that the
	 * first month -total=0 -total+=(nxtMonth-currentMonth) 3) store all of the
	 * totals 4) if the total was negetive, lower order 5) HOW TO DETERMIN HOW
	 * MUCH TO INCREASE OR DECREASE THE ORDER PLACED?
	 * 
	 * 
	 * 
	 */
	public ArrayList<String> toStringList() {
		
		ArrayList<String> list = new ArrayList<>();
		
		Map<String, ArrayList<String>> sortedAddress = new TreeMap<>(address);
		
		for (Map.Entry<String, ArrayList<String>> entry : sortedAddress.entrySet()) {
			for (String s : entry.getValue()) {
				list.add(map.get(s).toString());
			}
		}
		
		return list;
	}

	public int size() {
		return map.size();
	}

}
