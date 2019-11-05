package com.ev.BankFileFormat.Adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ev.BankFileFormat.Constants.CommonConstants;
import com.ev.BankFileFormat.Exception.BankFileFormatException;
import com.ev.BankFileFormat.Model.CSVConfig;
import com.ev.BankFileFormat.Model.HeaderConfig;

public class Reader {

	private static XSSFWorkbook myExcelBook;
	private static HSSFWorkbook myHssfWorkBook;

	/*
	 * reads data from excel file and based on the cellType generating a
	 * dataLines list containing all the rows in the sheet.Further processing
	 * the list to obtain the required configuration format rows
	 */
	public static List<String> readDataFromExcel(File xlsFile,
			String excelExtension, List<HeaderConfig> headerConfigs)
			throws BankFileFormatException {
		List<String> dataLinesList = null;
		List<String> rowList = null;
		Sheet myExcelSheet = null;
		try {
			FileInputStream fis = new FileInputStream(xlsFile);
			switch (excelExtension) {
			case CommonConstants.XLSX:
				myExcelBook = new XSSFWorkbook(fis);
				myExcelSheet = myExcelBook.getSheetAt(0);
				break;
			case CommonConstants.XLS:

				myHssfWorkBook = new HSSFWorkbook(fis);
				myExcelSheet = myHssfWorkBook.getSheetAt(0);
				break;
			}

			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = myExcelSheet.iterator();
			dataLinesList = new ArrayList<String>();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				StringBuffer lineBuffer = new StringBuffer();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					CellType type = cell.getCellType();
					switch (type) {
					case BOOLEAN:
						lineBuffer.append(cell.getBooleanCellValue() + ",");
						break;
					case NUMERIC:
						String cellValue = String.valueOf(cell
								.getNumericCellValue());
						StringBuffer cellValueBuffer = new StringBuffer("");
						if (cellValue.contains("E") || cellValue.contains("e")) {
							cellValueBuffer.append((new Double(cellValue))
									.longValue());
						} else {
							cellValueBuffer.append(cellValue);
						}

						lineBuffer.append(cellValueBuffer + ",");
						break;
					case STRING:
						lineBuffer.append(cell.getStringCellValue() + ",");
						break;
					case BLANK:
						lineBuffer.append("" + ",");
						break;
					default:
						lineBuffer.append(cell + ",");
						break;
					}

				}

				dataLinesList.add(lineBuffer.toString());
			}
			rowList = new ArrayList<String>();
			for (int rowIndex = 0; rowIndex < dataLinesList.size(); rowIndex++) {
				String row = dataLinesList.get(rowIndex);
				StringBuffer sb = new StringBuffer();
				sb = getRowsFromDataFile(row, headerConfigs);
				rowList.add(sb.toString());
			}
		} catch (FileNotFoundException e) {
			throw new BankFileFormatException(e.getMessage());
		} catch (IOException e) {
			throw new BankFileFormatException(e.getMessage());
		}
		return rowList;

	}

	/*
	 * returns the List of rows obtained from the csv file
	 */
	public static List<String> readDataFromCSV(File csvFile,
			List<HeaderConfig> headerConfigs) throws BankFileFormatException {
		List<String> rowList = null;
		Path pathToFile = Paths.get(csvFile.getAbsolutePath());
		try (BufferedReader br = Files.newBufferedReader(pathToFile,
				StandardCharsets.US_ASCII)) {
			// read the first line from the text file
			String row = br.readLine();
			// loop until all lines are read
			rowList = new ArrayList<String>();
			while (row != null) {
				StringBuffer sb = new StringBuffer();
				sb = getRowsFromDataFile(row, headerConfigs);
				rowList.add(sb.toString());
				row = br.readLine();
			}

		} catch (IOException | BankFileFormatException e) {
			throw new BankFileFormatException(e.getMessage());
		}
		return rowList;
	}

	/*
	 * reads row data from dat or excel or csv file and return the row data as
	 * StringBuffer value
	 */
	public static StringBuffer getRowsFromDataFile(String row,
			List<HeaderConfig> headerConfigs) throws BankFileFormatException {
		String[] dataArr = row.split(",");
		/*
		 * This condition is for those if dat file extension changed to CSV then
		 * all content in first line of dat file will be added to first cell of
		 * every row in csv file
		 */
		if (dataArr.length < 2)
			throw new BankFileFormatException(
					"File format changed. csv file does not contain the data properly");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dataArr.length; i++) {
			CSVConfig csvConfig = (CSVConfig) headerConfigs.get(i);
			int cellIndex = csvConfig.getCellIndex();
			allowMandatoryData(false, csvConfig, dataArr[cellIndex]);
			sb = filterBasedOnDataType(sb, csvConfig, dataArr[cellIndex]);
		}
		return sb;

	}

	/*
	 * filters the mandatory and non mandatory column values
	 */
	private static void allowMandatoryData(boolean isColumnMandatory,
			HeaderConfig headerConfig, String columnValue)
			throws BankFileFormatException {
		CSVConfig csvConfig = (CSVConfig) headerConfig;
		String columnName = csvConfig.getColumnName();
		int cellIndex = csvConfig.getCellIndex();
		isColumnMandatory = csvConfig.getColumnMandatory().equalsIgnoreCase(
				"yes") ? true : false;
		boolean allowData = (isColumnMandatory && !columnValue.isEmpty()) ? true
				: ((isColumnMandatory && columnValue.isEmpty()) ? false : true);
		if (!allowData)
			throw new BankFileFormatException("Column Name " + columnName
					+ " at index " + cellIndex
					+ " is mandatory. It cannot be empty");

	}

	/*
	 * Filters the column value based on its data type mentioned in the
	 * configuration
	 */
	public static StringBuffer filterBasedOnDataType(StringBuffer sb,
			HeaderConfig headerConfig, String columnValue)
			throws BankFileFormatException {
		try {
			String configurationDataType = headerConfig.getColumnDataType();
			StringBuffer dataTypeBuffer = new StringBuffer();
			int requiredLength = columnValue.length() > headerConfig
					.getColumnSize() ? headerConfig.getColumnSize()
					: columnValue.length();
			switch (configurationDataType) {
			case CommonConstants.DATATYPE_BOOLEAN:
				Boolean boolDataType = Boolean.valueOf(dataTypeBuffer.append(
						columnValue, 0, requiredLength).toString());
				sb.append(boolDataType).append(",");
				break;
			case CommonConstants.DATATYPE_NUMBER:
				Double dblDataType = Double.parseDouble(dataTypeBuffer.append(
						columnValue, 0, requiredLength).toString());
				sb.append(dblDataType).append(",");
				break;
			case CommonConstants.DATATYPE_DATE:
				if (requiredLength != headerConfig.getColumnSize())
					throw new BankFileFormatException(
							"Date field should have the fixed length.");
				String dateToParse = dataTypeBuffer.append(columnValue, 0,
						requiredLength).toString();
				String parsedDate = parseDateToCommonFormat(dateToParse);
				sb.append(parsedDate).append(",");
				break;
			default:
				String strDataType = dataTypeBuffer.append(columnValue, 0,
						requiredLength).toString();
				sb.append(strDataType).append(",");	
				break;
			}
		} catch (NumberFormatException nf) {
			throw new BankFileFormatException(
					"NumberFormatException: Expecting Number "
							+ nf.getMessage()
							+ "\n Kindly re-check the file and send");
		}
		return sb;
	}

	private static String parseDateToCommonFormat(String dateToParse) {
		try {
		SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MM-yyyy");
		Date date = sdfSource.parse(dateToParse);
		SimpleDateFormat sdfDestination = new SimpleDateFormat("dd-MM-yyyy");	
		dateToParse = sdfDestination.format(date);
		} catch (ParseException e) {
			System.out.println(e);
		}
		return dateToParse;
	}

}
