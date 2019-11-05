package com.ev.BankFileFormat.Model;


public class HeaderConfig {
	private String columnName;
	private Integer columnSize;
	private String columnDataType;
	private String columnMandatory;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public HeaderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(Integer columnSize) {
		this.columnSize = columnSize;
	}

	public String getColumnDataType() {
		return columnDataType;
	}

	public void setColumnDataType(String columnDataType) {
		this.columnDataType = columnDataType;
	}

	public String getColumnMandatory() {
		return columnMandatory;
	}

	public void setColumnMandatory(String columnMandatory) {
		this.columnMandatory = columnMandatory;
	}

	@Override
	public String toString() {
		return "HeaderConfig [columnName=" + columnName + ", columnSize="
				+ columnSize + ", columnDataType=" + columnDataType
				+ ", columnMandatory=" + columnMandatory + "]";
	}

	public HeaderConfig(String columnName, Integer columnSize,
			String columnDataType, String columnMandatory) {
		super();
		this.columnName = columnName;
		this.columnSize = columnSize;
		this.columnDataType = columnDataType;
		this.columnMandatory = columnMandatory;
	}
	
	
}
