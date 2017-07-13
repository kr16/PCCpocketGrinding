package vrsiModules;

public class VRSIemptyFastener {
	private String holeID;				//hole ID/fastener ID
	private double pinDiameter; 		//VRSI Measured average pin diameter
	private double pinMaximumDepth;		//VRSI Measured maximum pin depth
	private double pinMinimumDepth;		//VRSI Measured minimum pin depth
	private double pinAverageDepth;		//VRSI Measured average pin depth
	private double fillVolume;			//VRSI Calculated required volume for fastener fill
	private double posX;				//VRSI Measured pin position
	private double posY;				//VRSI Measured pin position
	private double posZ;				//VRSI Measured pin position
	private double rotC;				//VRSI Measured	pin vector
	private double rotB; 				//VRSI Measured	pin vector
	private double rotA; 				//VRSI Measured	pin vector
	
	public VRSIemptyFastener() {
		
	}
	
	public VRSIemptyFastener(VRSIemptyFastener origObject) {
		this.holeID = origObject.holeID;
		this.pinDiameter = origObject.pinDiameter;
		this.pinMaximumDepth = origObject.pinMaximumDepth;
		this.pinMinimumDepth = origObject.pinMinimumDepth;
		this.pinAverageDepth = origObject.pinAverageDepth;
		this.fillVolume = origObject.fillVolume;
		this.posX = origObject.posX;
		this.posY = origObject.posY;
		this.posZ = origObject.posZ;
		this.rotC = origObject.rotC;
		this.rotB = origObject.rotB;
		this.rotA = origObject.rotA;
	}
	
	
	public VRSIemptyFastener(double pinDiameter, double pinMaximumDepth, double pinMinimumDepth, double pinAverageDepth,
			double fillVolume, double posX, double posY, double posZ, double rotC, double rotB, double rotA) {
		super();
		this.pinDiameter = pinDiameter;
		this.pinMaximumDepth = pinMaximumDepth;
		this.pinMinimumDepth = pinMinimumDepth;
		this.pinAverageDepth = pinAverageDepth;
		this.fillVolume = fillVolume;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotC = rotC;
		this.rotB = rotB;
		this.rotA = rotA;
	}
	
	public double getPinDiameter() {
		return pinDiameter;
	}
	public void setPinDiameter(double pinDiameter) {
		this.pinDiameter = pinDiameter;
	}
	public double getPinMaximumDepth() {
		return pinMaximumDepth;
	}
	public void setPinMaximumDepth(double pinMaximumDepth) {
		this.pinMaximumDepth = pinMaximumDepth;
	}
	public double getPinMinimumDepth() {
		return pinMinimumDepth;
	}
	public void setPinMinimumDepth(double pinMinimumDepth) {
		this.pinMinimumDepth = pinMinimumDepth;
	}
	public double getPinAverageDepth() {
		return pinAverageDepth;
	}
	public void setPinAverageDepth(double pinAverageDepth) {
		this.pinAverageDepth = pinAverageDepth;
	}
	public double getFillVolume() {
		return fillVolume;
	}
	public void setFillVolume(double fillVolume) {
		this.fillVolume = fillVolume;
	}
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	public double getPosZ() {
		return posZ;
	}
	public void setPosZ(double posZ) {
		this.posZ = posZ;
	}
	public double getRotC() {
		return rotC;
	}
	public void setRotC(double rotC) {
		this.rotC = rotC;
	}
	public double getRotB() {
		return rotB;
	}
	public void setRotB(double rotB) {
		this.rotB = rotB;
	}
	public double getRotA() {
		return rotA;
	}
	public void setRotA(double rotA) {
		this.rotA = rotA;
	}

	public String getHoleID() {
		return holeID;
	}

	public void setHoleID(String holeID) {
		this.holeID = holeID;
	}

	@Override
	public String toString() {
		return "VRSIemptyFastener [holeID=" + holeID + ", pinDiameter="
				+ pinDiameter + ", pinMaximumDepth=" + pinMaximumDepth
				+ ", pinMinimumDepth=" + pinMinimumDepth + ", pinAverageDepth="
				+ pinAverageDepth + ", fillVolume=" + fillVolume + ", posX="
				+ posX + ", posY=" + posY + ", posZ=" + posZ + ", rotC=" + rotC
				+ ", rotB=" + rotB + ", rotA=" + rotA + "]";
	}

	

	
	
}
