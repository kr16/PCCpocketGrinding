package vrsiModules;

public class VRSIfillFastener {
	private String holeID;						//hole ID/fastener ID
	private double flushnessMaxDepth;			//VRSI Measured maximum flushness depth
	private double flushnessMinDepth;			//VRSI Measured minimum flushness depth
	private double flushnessAverageDepth;		//VRSI Measured average flushness depth
	private int pinDiameterVoidDefectCount;		//VRSI Number of void defects inside the pin diameter
	/**
	 * VRSI Number of missing material defects inside the pin diameter
	 */
	private int pinDiameterMMDefectCount;		//VRSI Number of missing material defects inside the pin diameter
	private int pinDiameterSpeckDefectCount;	//VRSI Number of speck defects inside the pin diameter
	private int pinDiameterBubbleDefectCount; //VRSI Number of bubble defects inside the pin diameter
	private int outPinDiameterVoidDefectCount;		//VRSI Number of void defects outside the pin diameter
	/**
	 * VRSI Number of missing material defects outside the pin diameter
	 */
	private int outPinDiameterMMDefectCount;		//VRSI Number of missing material defects outside the pin diameter
	private int outPinDiameterSpeckDefectCount;		//VRSI Number of speck defects outside the pin diameter
	private int outPinDiameterBubbleDefectCount;	//VRSI Number of bubble defects outside the pin diameter

	public VRSIfillFastener() {
		
	}
	
	public VRSIfillFastener(VRSIfillFastener origObject) {
		this.holeID = origObject.holeID;
		this.flushnessMaxDepth = origObject.flushnessMaxDepth;
		this.flushnessMinDepth = origObject.flushnessMinDepth;
		this.flushnessAverageDepth = origObject.flushnessAverageDepth;
		this.pinDiameterVoidDefectCount = origObject.pinDiameterVoidDefectCount;
		this.pinDiameterMMDefectCount = origObject.pinDiameterMMDefectCount;
		this.pinDiameterSpeckDefectCount = origObject.pinDiameterSpeckDefectCount;
		this.pinDiameterBubbleDefectCount = origObject.pinDiameterBubbleDefectCount;
		this.outPinDiameterVoidDefectCount = origObject.outPinDiameterVoidDefectCount;
		this.outPinDiameterMMDefectCount = origObject.outPinDiameterMMDefectCount;
		this.outPinDiameterSpeckDefectCount = origObject.outPinDiameterSpeckDefectCount;
		this.outPinDiameterBubbleDefectCount = origObject.outPinDiameterBubbleDefectCount;
	}
	
	public VRSIfillFastener(double flushnessMaxDepth, double flushnessMinDepth, double flushnessAverageDepth,
			int pinDiameterVoidDefectCount, int pinDiameterMMDefectCount, int pinDiameterSpeckDefectCount,
			int pinDiameterBubbleDefectCount, int outPinDiameterVoidDefectCount, int outPinDiameterMMDefectCount,
			int outPinDiameterSpeckDefectCount, int outPinDiameterBubbleDefectCount) {
		super();
		this.flushnessMaxDepth = flushnessMaxDepth;
		this.flushnessMinDepth = flushnessMinDepth;
		this.flushnessAverageDepth = flushnessAverageDepth;
		this.pinDiameterVoidDefectCount = pinDiameterVoidDefectCount;
		this.pinDiameterMMDefectCount = pinDiameterMMDefectCount;
		this.pinDiameterSpeckDefectCount = pinDiameterSpeckDefectCount;
		this.pinDiameterBubbleDefectCount = pinDiameterBubbleDefectCount;
		this.outPinDiameterVoidDefectCount = outPinDiameterVoidDefectCount;
		this.outPinDiameterMMDefectCount = outPinDiameterMMDefectCount;
		this.outPinDiameterSpeckDefectCount = outPinDiameterSpeckDefectCount;
		this.outPinDiameterBubbleDefectCount = outPinDiameterBubbleDefectCount;
	}


	public double getFlushnessMaxDepth() {
		return flushnessMaxDepth;
	}


	public void setFlushnessMaxDepth(double flushnessMaxDepth) {
		this.flushnessMaxDepth = flushnessMaxDepth;
	}


	public double getFlushnessMinDepth() {
		return flushnessMinDepth;
	}


	public void setFlushnessMinDepth(double flushnessMinDepth) {
		this.flushnessMinDepth = flushnessMinDepth;
	}


	public double getFlushnessAverageDepth() {
		return flushnessAverageDepth;
	}


	public void setFlushnessAverageDepth(double flushnessAverageDepth) {
		this.flushnessAverageDepth = flushnessAverageDepth;
	}


	public int getPinDiameterVoidDefectCount() {
		return pinDiameterVoidDefectCount;
	}


	public void setPinDiameterVoidDefectCount(int pinDiameterVoidDefectCount) {
		this.pinDiameterVoidDefectCount = pinDiameterVoidDefectCount;
	}


	public int getPinDiameterMMDefectCount() {
		return pinDiameterMMDefectCount;
	}


	public void setPinDiameterMMDefectCount(int pinDiameterMMDefectCount) {
		this.pinDiameterMMDefectCount = pinDiameterMMDefectCount;
	}


	public int getPinDiameterSpeckDefectCount() {
		return pinDiameterSpeckDefectCount;
	}


	public void setPinDiameterSpeckDefectCount(int pinDiameterSpeckDefectCount) {
		this.pinDiameterSpeckDefectCount = pinDiameterSpeckDefectCount;
	}


	public int getPinDiameterBubbleDefectCount() {
		return pinDiameterBubbleDefectCount;
	}


	public void setPinDiameterBubbleDefectCount(int pinDiameterBubbleDefectCount) {
		this.pinDiameterBubbleDefectCount = pinDiameterBubbleDefectCount;
	}


	public int getOutPinDiameterVoidDefectCount() {
		return outPinDiameterVoidDefectCount;
	}


	public void setOutPinDiameterVoidDefectCount(int outPinDiameterVoidDefectCount) {
		this.outPinDiameterVoidDefectCount = outPinDiameterVoidDefectCount;
	}


	public int getOutPinDiameterMMDefectCount() {
		return outPinDiameterMMDefectCount;
	}


	public void setOutPinDiameterMMDefectCount(int outPinDiameterMMDefectCount) {
		this.outPinDiameterMMDefectCount = outPinDiameterMMDefectCount;
	}


	public int getOutPinDiameterSpeckDefectCount() {
		return outPinDiameterSpeckDefectCount;
	}


	public void setOutPinDiameterSpeckDefectCount(int outPinDiameterSpeckDefectCount) {
		this.outPinDiameterSpeckDefectCount = outPinDiameterSpeckDefectCount;
	}


	public int getOutPinDiameterBubbleDefectCount() {
		return outPinDiameterBubbleDefectCount;
	}


	public void setOutPinDiameterBubbleDefectCount(int outPinDiameterBubbleDefectCount) {
		this.outPinDiameterBubbleDefectCount = outPinDiameterBubbleDefectCount;
	}

	public String getHoleID() {
		return holeID;
	}

	public void setHoleID(String holeID) {
		this.holeID = holeID;
	}

	@Override
	public String toString() {
		return "VRSIfillFastener [holeID=" + holeID + ", flushnessMaxDepth="
				+ flushnessMaxDepth + ", flushnessMinDepth="
				+ flushnessMinDepth + ", flushnessAverageDepth="
				+ flushnessAverageDepth + ", pinDiameterVoidDefectCount="
				+ pinDiameterVoidDefectCount + ", pinDiameterMMDefectCount="
				+ pinDiameterMMDefectCount + ", pinDiameterSpeckDefectCount="
				+ pinDiameterSpeckDefectCount
				+ ", pinDiameterBubbleDefectCount="
				+ pinDiameterBubbleDefectCount
				+ ", outPinDiameterVoidDefectCount="
				+ outPinDiameterVoidDefectCount
				+ ", outPinDiameterMMDefectCount="
				+ outPinDiameterMMDefectCount
				+ ", outPinDiameterSpeckDefectCount="
				+ outPinDiameterSpeckDefectCount
				+ ", outPinDiameterBubbleDefectCount="
				+ outPinDiameterBubbleDefectCount + "]";
	}
	

}
