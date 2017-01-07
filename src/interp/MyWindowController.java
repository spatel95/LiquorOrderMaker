package interp;

import nl.knaw.dans.common.dbflib.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import analyze.Analyzer;
import analyze.Container;
import exception.BrandDoesNotExistException;


public class MyWindowController {
	Analyzer analyzer;
	File file;
	public MyWindowController(File fileIn){
//		try {
//			analyzer = new Analyzer(new Table(fileIn));
//		} catch (CorruptedTableException e) {
////			ErrorBox.display("Data Not Found", "Please Select a .dbf file");
//			e.printStackTrace();
//		}
		
		try {
			analyzer = new Analyzer(new Table(fileIn));
		} catch (CorruptedTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		file = fileIn;
	}
	
	public File getFile(){
		return file;
	}
	
	public ArrayList<String> getBrandList(){
		return analyzer.getBrandList();
	}
	
	public ArrayList<Container> getAll(String s){
		if(s == null){
//			System.out.println("WHY IS THE PARAM NULL?? \n\n\n");
			return new ArrayList<>();
		}
		try {
			return analyzer.getAll(s);
		} catch (BrandDoesNotExistException e) {
			ErrorBox.display("Error", "The Brand That Was Selected Did Not Exist \n"
									+ "This Normally Cannot Occur On Its Own.\n"
									+"The Only Probably Cause Would be .dbf File being Currupted");
			return new ArrayList<>();
		}
	}
	
	public double getStdDiv(String barcode,int months){
		try {
			return analyzer.getStdDiv(barcode, months);
		} catch (BrandDoesNotExistException e) {
			e.printStackTrace();
		}
		return -1.0;
	}
	
	public double getVariance(String barcode,int months){
		try {
			return analyzer.getVariance(barcode, months);
		} catch (BrandDoesNotExistException e) {
			e.printStackTrace();
		}
		return -1.0;
	}
	
	public double getAverage(String barcode,int months){
		try {
			return analyzer.getAverage(barcode, months);
		} catch (BrandDoesNotExistException e) {
			e.printStackTrace();
		}
		return -1.0;
	}
	
	public String analyzeData(List<String> list,int months){
		try{
			return analyzer.analyzeData(list, months);
		}catch(BrandDoesNotExistException e){
			System.out.println("BRAND "+e.getMessage()+" DOES NOT EXISIT");
			return "One oof the brands did not exisist bitches!";
		}
	}
	
	ArrayList<Integer> simpleOrderList(String barcode, int months){
		try {
			return analyzer.simpleOrderItem(barcode, months);
		} catch (BrandDoesNotExistException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public int getQty(String barcode) throws BrandDoesNotExistException{
        return analyzer.getMap().get(barcode).getQTY().getValue();
    }
	
}
