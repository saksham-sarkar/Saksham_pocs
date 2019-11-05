package com.ev.BankFileFormat.Adapter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.ev.BankFileFormat.Constants.CommonConstants;
import com.ev.BankFileFormat.Exception.BankFileFormatException;
import com.ev.BankFileFormat.Model.HeaderConfig;
import com.ev.BankFileFormat.Model.NewDatConfig;

public class SaveToCSV {

	/*
	 * Saves the final generated the data content to the .CSV output file
	 */
	public static void saveToCSVFile(String[] dataLinesArr,
			List<HeaderConfig> headerConfigs, String formatName,
			String outputFile) throws BankFileFormatException {

		try {
			StringBuffer finalContent = fetchContentForSaving(headerConfigs,
					formatName, dataLinesArr);
			FileWriter csvWriter = new FileWriter(outputFile, false);
			csvWriter.append(finalContent);
			csvWriter.flush();
			csvWriter.close();
			System.out.println("CSV Output generated successfully");
		} catch (IOException e) {
			throw new BankFileFormatException(e.getMessage());
		}
	}

	public static StringBuffer fetchContentForSaving(
			List<HeaderConfig> headerConfigs, String formatName,
			String[] dataLinesArr) throws BankFileFormatException {
		StringBuffer sb = new StringBuffer();
		int i = 0;

		String extension = formatName
				.substring(formatName.lastIndexOf("_") + 1);
		switch (extension) {
		case CommonConstants.XLS:
		case CommonConstants.XLSX:
		case CommonConstants.CSV:
			while (i < headerConfigs.size()) {
				sb.append(headerConfigs.get(i).getColumnName()).append(",");
				i++;
			}
			for (int index = 0; index < dataLinesArr.length; index++) {
				sb.append("\n");
				String[] dataCells = dataLinesArr[index].split(",");
				for (int j = 0; j < headerConfigs.size(); j++) {
					sb.append(dataCells[j]).append(",");
				}
			}
			break;
		case CommonConstants.DAT:
			while (i < headerConfigs.size()) {
				NewDatConfig ndtConfig = (NewDatConfig) headerConfigs.get(i);
				sb.append(ndtConfig.getDispname()).append(",");
				i++;
			}
			for (int index = 0; index < dataLinesArr.length; index++) {
				sb.append("\n");
				for (int j = 0; j < headerConfigs.size(); j++) {
					NewDatConfig datConfig = (NewDatConfig) headerConfigs
							.get(j);
					String columnValue = dataLinesArr[index].substring(
							datConfig.getColumnStartIndex(),
							datConfig.getColumnEndIndex() + 1);
					try {
						sb = Reader.filterBasedOnDataType(sb, datConfig,
								columnValue);
					} catch (BankFileFormatException e) {
						throw new BankFileFormatException(e.getMessage());
					}
					/*
					 * sb.append(dataLinesArr[index],
					 * datConfig.getColumnStartIndex(),
					 * datConfig.getColumnEndIndex() + 1).append(",");
					 */
				}
			}
			break;
		}
		return sb;
	}

}
