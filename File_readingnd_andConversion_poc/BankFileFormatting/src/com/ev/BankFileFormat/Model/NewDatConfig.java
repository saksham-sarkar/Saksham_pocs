package com.ev.BankFileFormat.Model;

public class NewDatConfig extends DATConfig{
	private String dispname;
	private String source;
	private String comment;
	public String getDispname() {
		return dispname;
	}
	public void setDispname(String dispname) {
		this.dispname = dispname;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public NewDatConfig(String dispname, String source,
			String comment) {
		super();
		this.dispname = dispname;
		this.source = source;
		this.comment = comment;
	}
	public NewDatConfig() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "NewDatConfig [dispname=" + dispname 
				+ ", source=" + source + ", comment=" + comment
				 + ", columnEndIndex="
				+ getColumnEndIndex() + ", columnStartIndex="
				+ getColumnStartIndex()
				+ ", columnName=" + getColumnName() + ", columnSize="
				+ getColumnSize() + ", columnDataType="
				+ getColumnDataType() + ", columnMandatory="
				+ getColumnMandatory() + "]";
	}
	
	
	
	
}
