package interp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exception.NonExistentTableException;
import nl.knaw.dans.common.dbflib.*;

public class Tester {

	public static void printField(Table t) throws NonExistentTableException {
		if (t == null) {
			throw new NonExistentTableException("The Table is Null");
		}

		List<Field> fieldList = t.getFields();
		// String s = new String();

		for (Field f : fieldList) {
			System.out.println(f.getName() + " Type - " + f.getType().toString());
		}

	}

	public static void tableInfo(Table t) throws CorruptedTableException, IOException {
		if (t == null) {
			System.out.println("ERROR-INFO"); // Exception
		}

		System.out.println("Record Count - " + t.getRecordCount());
		int numberOfRecords = t.getRecordCount();
		for (int i = 0; i < numberOfRecords; i++) {
			Record record = t.getRecordAt(i);
			System.out.println(record.getStringValue("BRAND") + "  " + record.getStringValue("DESCRIP") + "  "
					+ record.getNumberValue("SALES"));
		}
	}

	public static void main(String[] args) throws CorruptedTableException, IOException, NonExistentTableException {
		if (args.length != 1) {
			System.out.println("ERROR"); // create a exception class
			// what else should i put in this? exit?
		}
		File file = new File(args[0]);
		if (file != null) {
			System.out.println("You done good son");
		}

		Table t = new Table(file);
		try {
			t.open();
		} catch (CorruptedTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		printField(t);
		// tableInfo(t);

		try {
			t.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
