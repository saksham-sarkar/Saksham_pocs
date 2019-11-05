package com.ev.BankFileFormat.Adapter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnmappableCharacterException;
import java.nio.file.Files;
import java.util.List;

import com.ev.BankFileFormat.Constants.CommonConstants;
import com.ev.BankFileFormat.Exception.BankFileFormatException;
import com.ev.BankFileFormat.Model.HeaderConfig;

public class Router {

	/*
	 * @description takes the data file and configuration header json file as input 
	 * and routes the data based on the file type 
	 */
	public static void routeDataBasedOnFile(File dataFile, File headerFile)
			throws BankFileFormatException {
		String absolutePath = dataFile.getAbsolutePath();
		String[] formatAr = absolutePath.split("\\.");
		String formatName = formatAr[0]
				.substring(formatAr[0].lastIndexOf("_") + 1)
				+ "_"
				+ formatAr[1];
		String extension = absolutePath
				.substring(absolutePath.lastIndexOf(".") + 1);
		try {
			List<HeaderConfig> headerConfigs = Converter.getListOfHeaders(
					headerFile, formatName);

			List<String> rowDataLines = fetchRowsBasedOnFileType(dataFile,
					extension, headerConfigs);
			for (HeaderConfig hc : headerConfigs)
				System.out.println(hc.toString());

			String outputFile = CommonConstants.OUTPUT_PATH + formatName
					+ CommonConstants.OUTPUT_CSV;
			SaveToCSV.saveToCSVFile(
					rowDataLines.toArray(new String[rowDataLines.size()]),
					headerConfigs, formatName, outputFile);
		} catch (IOException | BankFileFormatException e) {
			throw new BankFileFormatException(e.getMessage());
		}
	}

	/*
	 * @description fetches the rows or lines based on the fileType from the data file
	 */
	public static List<String> fetchRowsBasedOnFileType(File dataFile,
			String extension, List<HeaderConfig> headerConfigs)
			throws IOException, BankFileFormatException {
		List<String> rowDataLines = null;
		try {
			switch (extension) {
			case CommonConstants.DAT:
				rowDataLines = Files.readAllLines(dataFile.toPath(),
						Charset.defaultCharset());
				break;
			case CommonConstants.XLS:
			case CommonConstants.XLSX:
				rowDataLines = Reader.readDataFromExcel(dataFile, extension,
						headerConfigs);
				break;
			case CommonConstants.CSV:
				rowDataLines = Reader.readDataFromCSV(dataFile, headerConfigs);
				break;
			}
			if(headerConfigs.isEmpty())
				throw new BankFileFormatException("Configuration json does not contain the entry for this file/format");
			for (String line : rowDataLines)
				System.out.println(line);
		} catch (UnmappableCharacterException uce) {
			throw new BankFileFormatException("Wrong or misplaced file extension. Not a dat file");
		}
		return rowDataLines;
	}

}
