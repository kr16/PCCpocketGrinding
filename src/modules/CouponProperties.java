package modules;

import modules.Common;
import modules.Common.ECouponSectionName;

public class CouponProperties {
	private int startRow;
	private int startColumn;
	private int rowsMax; 
	private int columnsMax;
	private double rowsOffset;
	private double columnsOffset;
	private double fastenerOffset;
	
	
	public int getStartRow() {
		return startRow;
	}
	public int getStartColumn() {
		return startColumn;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}
	public int getRowsMax() {
		return rowsMax;
	}
	public int getColumnsMax() {
		return columnsMax;
	}
	public double getRowsOffset() {
		return rowsOffset;
	}
	public double getColumnsOffset() {
		return columnsOffset;
	}
	public void setRowsMax(int rowsMax) {
		this.rowsMax = rowsMax;
	}
	public void setColumnsMax(int columnsMax) {
		this.columnsMax = columnsMax;
	}
	public void setRowsOffset(double rowsOffset) {
		this.rowsOffset = rowsOffset;
	}
	public void setColumnsOffset(double columnsOffset) {
		this.columnsOffset = columnsOffset;
	}
	
	public double getFastenerOffset() {
		return fastenerOffset;
	}

	public void setFastenerOffset(double fastenerOffset) {
		this.fastenerOffset = fastenerOffset;
	}

	public CouponProperties(ECouponSectionName name) {
		String globalsFilePath = "d:/Transfer/UserXMLs/";
		String globalsFileNamePLC = "GlobalVarsHotDotPLC.xml";
		
		XmlParserGlobalVarsRD globalsFromPLC = new XmlParserGlobalVarsRD(globalsFilePath,globalsFileNamePLC);
		
		switch (name) {
		case Coupon14:
			setRowsOffset(globalsFromPLC.getVarDouble("rowOffset1through4"));
			setColumnsOffset(globalsFromPLC.getVarDouble("columnOffset14"));
			setRowsMax(6);
			setColumnsMax(10);
			setFastenerOffset(4.5);
			break;
		case Coupon56:
			setRowsOffset(globalsFromPLC.getVarDouble("rowOffset5through6"));
			setColumnsOffset(globalsFromPLC.getVarDouble("columnOffset56"));
			setRowsMax(6);
			setColumnsMax(10);
			setFastenerOffset(7.5);
			break;
		case Coupon79:
			setRowsOffset(globalsFromPLC.getVarDouble("rowOffset7through9"));
			setColumnsOffset(globalsFromPLC.getVarDouble("columnOffset79"));
			setRowsMax(3);
			setColumnsMax(7);
			setFastenerOffset(9.5);
			break;
		default:
			break;
		}
	}
}
