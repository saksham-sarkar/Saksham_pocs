package com.ev.BankFileFormat.Model;

public class CSVConfig extends HeaderConfig {

	private int cellIndex;

	public int getCellIndex() {
		return cellIndex;
	}

	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}

	public CSVConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CSVConfig(String columnName, Integer columnSize,
			String columnDataType, String columnMandatory) {
		super(columnName, columnSize, columnDataType, columnMandatory);
		// TODO Auto-generated constructor stub
	}

	public CSVConfig(int cellIndex) {
		super();
		this.cellIndex = cellIndex;
	}

	@Override
	public String toString() {
		return "CSVConfig [cellIndex=" + cellIndex + ", columnName="
				+ getColumnName() + ", columnSize=" + getColumnSize()
				+ ", columnDataType=" + getColumnDataType()
				+ ", columnMandatory=" + getColumnMandatory() + "]";
	}

}
