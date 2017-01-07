package interp;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import analyze.Analyzer;
import nl.knaw.dans.common.dbflib.*;

public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.print("Enter Name of the File: ");

		File file = new File("src/" + s.next());
		Table table = new Table(file);
		System.out.println("TABLE COUnt: "+table.getRecordCount());
		// try {
		// table.open();
		System.out.println("Table Open");

		Analyzer tableMap;
		try {
			tableMap = new Analyzer(table);

			System.out.println("Analyzer OPEN");
			//
			// String stringTable = tableMap.toString();
			// tableMap.printAdress();
			// System.out.println(table.getRecordCount());
			// System.out.println(tableMap.size());
			System.out.println("Starting input");
			String in;

			// tableMap.tableMap.printAdress();
			while (true) {
				System.out.print("Enter Comannd: ");
				in = s.next();

				if (in.equals("order")) {
					System.out.println("Brand Name: ");
					String brand = s.next();
					System.out.println(brand);
					s.nextLine();
					System.out.println("Scope of Months: ");
					int time = s.nextInt();

					System.out.println(tableMap.order(brand, time));

				}

				if (in.equals("print")) {
					System.out.println("to File?");
					String printCommand = s.next();
					if (printCommand.equals("y") || printCommand.equals("Y") || printCommand.equals("yes")
							|| printCommand.equals("Yes") || printCommand.equals("YES")) {

						System.out.print("File Name: ");
						String fileName = s.next();

						tableMap.writeToFile(fileName);

					} else {
						System.out.println(tableMap.dataToString());
					}
				}

				if (in.equals("exit")) {

					break;
				}
			}

			s.close();

			// } catch (CorruptedTableException e) {
			// e.printStackTrace();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }

			// System.out.println("what would you like to do?");
			// String in = s.next();
			// while (!in.equals("exit")) {
			// if (in.equals("order")) {
			//
			// }
			// }

		} catch (CorruptedTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

/*
 * Ideas on how to organize data make a OBJ that holds all fields. one OBJ will
 * be created for each record upon calling the constructor of the worker class.
 * Stored in a Hash Brand -> item
 *
 *
 * 
 */
