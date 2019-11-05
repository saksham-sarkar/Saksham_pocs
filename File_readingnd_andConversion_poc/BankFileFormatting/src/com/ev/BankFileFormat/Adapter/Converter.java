package com.ev.BankFileFormat.Adapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ev.BankFileFormat.Constants.CommonConstants;
import com.ev.BankFileFormat.Exception.BankFileFormatException;
import com.ev.BankFileFormat.Model.CSVConfig;
import com.ev.BankFileFormat.Model.HeaderConfig;
import com.ev.BankFileFormat.Model.NewDatConfig;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Converter {
	private static List<HeaderConfig> headerList = null;

	/*
	 * Gets the list of headers based on the configuration updated against the
	 * file format and name
	 */
	public static List<HeaderConfig> getListOfHeaders(File headerFile,
			String formatName) throws BankFileFormatException {
		convertJsonToHeadersList(headerFile, formatName);

		return headerList;

	}

	/*
	 * Reads the Configuration JSON file and converts the data to the respective
	 * formatted object DAT or CSV headerConfig
	 */
	public static void convertJsonToHeadersList(File headerFile,
			String formatName) throws BankFileFormatException {
		headerList = new ArrayList<HeaderConfig>();
		try {
			ObjectMapper mapper = new ObjectMapper(); // create once, reuse
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			JsonNode root = mapper.readTree(headerFile);
			JsonNode foo = root.get(formatName);
			String extension = formatName
					.substring(formatName.lastIndexOf("_") + 1);
			if (foo != null) {
				switch (extension) {
				case CommonConstants.DAT:
					/*
					 * List<DATConfig> datConfigList = mapper.readerFor( new
					 * TypeReference<List<DATConfig>>() { }).readValue(foo); for
					 * (DATConfig datConfig : datConfigList) { HeaderConfig
					 * headerConfig = new
					 * NewDatConfig(datConfig.getColumnName(),
					 * datConfig.getColumnStartIndex
					 * (),datConfig.getColumnSize(),
					 * datConfig.getColumnDataType()
					 * ,datConfig.getColumnMandatory());
					 * headerList.add(headerConfig); }
					 */
					List<NewDatConfig> datConfigList = mapper.readerFor(
							new TypeReference<List<NewDatConfig>>() {
							}).readValue(foo);
					for (NewDatConfig datConfig : datConfigList) {
						datConfig.setColumnStartIndex(datConfig
								.getColumnStartIndex() - 1);
						datConfig.setColumnEndIndex(datConfig
								.getColumnStartIndex()
								+ datConfig.getColumnSize() - 1);
						HeaderConfig headerConfig = datConfig;
						headerList.add(headerConfig);
					}
					break;
				case CommonConstants.XLS:
				case CommonConstants.XLSX:
				case CommonConstants.CSV:
					List<CSVConfig> csvConfigList = mapper.readerFor(
							new TypeReference<List<CSVConfig>>() {
							}).readValue(foo);
					for (CSVConfig csvConfig : csvConfigList) {
						HeaderConfig headerConfig = csvConfig;
						headerList.add(headerConfig);
					}
					break;
				}
			}
		} catch (JsonProcessingException jpe) {
			throw new BankFileFormatException(jpe.getMessage());
		} catch (IOException ie) {
			throw new BankFileFormatException(ie.getMessage());
		}
	}
}
