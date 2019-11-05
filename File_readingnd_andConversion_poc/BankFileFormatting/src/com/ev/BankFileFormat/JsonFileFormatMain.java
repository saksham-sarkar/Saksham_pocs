package com.ev.BankFileFormat;
/*
 * @author saksham.sarkar
 * @Description The program reads file in .dat,.csv,.xls, .xlsx format, filters the data using the headers updated in the configuration JSON file
 *  and routes the output to a CSV file 
 */
import java.io.File;
import java.util.Scanner;

import com.ev.BankFileFormat.Adapter.Router;
import com.ev.BankFileFormat.Constants.CommonConstants;

public class JsonFileFormatMain {
	public static void main(String args[]) {
		System.out.println("Enter the file name present in given path");
		Scanner sc = new Scanner(System.in);
		String inputFile = sc.nextLine();
		File dataFile = new File(CommonConstants.INPUT_PATH + inputFile);
		// File dataFile = new File(args[0]);
		File headerFile = new File(CommonConstants.CONFIGURATION_JSON_PATH);

		try {
			Router.routeDataBasedOnFile(dataFile, headerFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		 sc.close();
	}

}
