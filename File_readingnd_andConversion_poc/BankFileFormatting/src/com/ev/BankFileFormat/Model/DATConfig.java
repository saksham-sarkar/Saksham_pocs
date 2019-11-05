package com.ev.BankFileFormat.Model;

public class DATConfig extends HeaderConfig {
	private Integer columnStartIndex;
	private Integer columnEndIndex;

	public Integer getColumnEndIndex() {
		return columnEndIndex;
	}

	public void setColumnEndIndex(Integer columnEndIndex) {
		this.columnEndIndex = columnEndIndex;
	}

	
	public Integer getColumnStartIndex() {
		return columnStartIndex;
	}

	public void setColumnStartIndex(Integer columnStartIndex) {
		this.columnStartIndex = columnStartIndex;
	}

	public DATConfig() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DATConfig(Integer columnStartIndex, Integer columnEndIndex) {
		super();
		this.columnStartIndex = columnStartIndex;
		this.columnEndIndex = columnEndIndex;
	}

	@Override
	public String toString() {
		return "DATConfig [columnStartIndex=" + columnStartIndex
				+ ", columnEndIndex=" + columnEndIndex + ", columnName="
				+ getColumnName() + ", columnSize=" + getColumnSize()
				+ ", columnDataType=" + getColumnDataType()
				+ ", columnMandatory=" + getColumnMandatory() + "]";
	}

}
