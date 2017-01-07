package analyze;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import exception.BrandDoesNotExistException;
import interp.MyWindowController;
import nl.knaw.dans.common.dbflib.*;

public class Analyzer {
	TableMap tableMap;
	public Analyzer(Table table) throws CorruptedTableException{
		try {
			table.open();		
			tableMap = new TableMap(table);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public String order(String brand, int numberOfMonths){
		final int BARCODE = 0;
		final int QTY = 1;
		final int SOLD = 2;
		final int ORDER = 3;
		
		brand = brand.toUpperCase();
		ArrayList<String[]> orderList = tableMap.order(brand, numberOfMonths);
		String toReturn = "BRAND \t DESCRIPTION \t\t\t \t size QTY \t SOLD \t TO ORDER\n";
		
		for(String[] s: orderList){
			try {
				toReturn += (brand + " -- " + tableMap.get(s[BARCODE]).getDescription() +" -- "+tableMap.get(s[BARCODE]).getSize()+" -- "+ s[QTY]+" -- "+s[SOLD]+" -- "+s[ORDER]+"\n");
			} catch (BrandDoesNotExistException e) {
				toReturn+="FILE NOT FOUNT \n";
				e.printStackTrace();
			}
			
		}
		
		return toReturn;
		
	}

	public TableMap getMap(){
        return tableMap;
    }
	
	public ArrayList<Integer> simpleOrderItem(String barcode, int months) throws BrandDoesNotExistException{
		ArrayList<Integer> list = new ArrayList<>(3);
		
		//list[0] = Quantity
		list.add(tableMap.get(barcode).getData("QTY_ON_HND").intValue());
		//list[1] = Sum of months sold in the past
		list.add(tableMap.get(barcode).pastQtySold(months));
		//list[2] = qty - sum --> how many expecty to order
		
		int toOrder = list.get(1)-list.get(0);
		if(toOrder < 0){
			toOrder = 0;
		}
		list.add(toOrder);
		
		return list;
	}
	
//	public TableMap getTablemap() {
//		return tableMap;
//	}
	
	public String dataToString(){
		
		ArrayList<String> list = tableMap.toStringList();
		String toReturn = new String();
		
		for (String s : list) {
			toReturn+= s+"\n";
		}
		
		return toReturn;
	}
	
	public void writeToFile(String fileName){
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"utf-8"));
			writer.write(this.dataToString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getBrandList(){
		return tableMap.getBrandList();
	}
	
	//Takes in Brandname
	public ArrayList<Container> getAll(String brand) throws BrandDoesNotExistException{
		brand = brand.toUpperCase();
		ArrayList<String> barcodeList = tableMap.getBrandList(brand);

		ArrayList <Container> list = new ArrayList<>();
		
		for (String bar : barcodeList) {
			list.add(tableMap.get(bar));
		}
		
		return list;
		
	}
	
	public String analyzeData(List<String> brand,int months) throws BrandDoesNotExistException{
		String s = new String();
		for (String string : brand) {
			
			s+="<==================================================================================>";
			s+="\n";
			s+="Brand:  "+string+'\n'+'\n';
			List<String> list = tableMap.getBrandList(string);
			for (String item : list) {
				double mean = getAverage(item, months);
				double var = getVariance(item, mean, months);
				double std = getStdDiv(var);
				
				s+="Descrption:  "+tableMap.barcodeToDescription(item)+'\n';
				s+="Size:  "+ tableMap.barcodeToSize(item)+'\n';

				s+="Mean: "+mean+'\n';
				s+="Variance:  "+var+'\n';
				s+="StandardDiv:  "+std+'\n'+'\n';
			}
			
			s+="<==================================================================================>"+'\n';
		}
		return s;
	}
	
	
	public double getAverage(String s,int months) throws BrandDoesNotExistException{
		Container c = tableMap.get(s);
		
		double sum = 0.0;
		for (int i = 1; i <= months; i++) {
			sum+=c.getSaleAtMonth(i);
		}
		
		return sum/months;
	}
	
	public double getVariance(String s,double avg,int months) throws BrandDoesNotExistException{
		Container c = tableMap.get(s);
		double var = 0.0;
		for (int i = 1; i <= months; i++) {
			var+=(avg-c.getSaleAtMonth(i))*(avg-c.getSaleAtMonth(i));
		}
		
		return var/months;
	}
	
	public double getVariance(String barcode,int months) throws BrandDoesNotExistException{
		double avg = getAverage(barcode,months);
		return getVariance(barcode, avg, months);
	}
	
	public double getStdDiv(double var){
		return Math.sqrt(var);
	}
	
	public double getStdDiv(String barcode,int months) throws BrandDoesNotExistException{
		double avg = getAverage(barcode, months);
		double var = getVariance(barcode, avg, months);
		return getStdDiv(var);
	}

	
	
	
//	private void stdDiv(String s) throws BrandDoesNotExistException{
//		Container c = tableMap.get(s);
//		
//		
//	}
	
	
}
