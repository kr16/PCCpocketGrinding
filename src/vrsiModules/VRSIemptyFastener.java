package vrsiModules;

public class VRSIemptyFastener {
	private double pinDiameter; 		//VRSI Measured average pin diameter
	private double pinMaximumDepth;		//VRSI Measured maximum pin depth
	private double pinMinimumDepth;		//VRSI Measured minimum pin depth
	private double pinAverageDepth;		//VRSI Measured average pin depth
	private double fillVolume;			//VRSI Calculated required volume for fastener fill
	private double posX;				//VRSI Measured pin position
	private double posY;				//VRSI Measured pin position
	private double posZ;				//VRSI Measured pin position
	private double rotI;				//VRSI Measured	pin vector
	private double rotJ; 				//VRSI Measured	pin vector
	private double rotK; 				//VRSI Measured	pin vector
	
	public VRSIemptyFastener(double pinDiameter, double pinMaximumDepth, double pinMinimumDepth, double pinAverageDepth,
			double fillVolume, double posX, double posY, double posZ, double rotI, double rotJ, double rotK) {
		super();
		this.pinDiameter = pinDiameter;
		this.pinMaximumDepth = pinMaximumDepth;
		this.pinMinimumDepth = pinMinimumDepth;
		this.pinAverageDepth = pinAverageDepth;
		this.fillVolume = fillVolume;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotI = rotI;
		this.rotJ = rotJ;
		this.rotK = rotK;
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
	public double getRotI() {
		return rotI;
	}
	public void setRotI(double rotI) {
		this.rotI = rotI;
	}
	public double getRotJ() {
		return rotJ;
	}
	public void setRotJ(double rotJ) {
		this.rotJ = rotJ;
	}
	public double getRotK() {
		return rotK;
	}
	public void setRotK(double rotK) {
		this.rotK = rotK;
	}

	
	
}
