package modules;

public interface Common {
	// Enumerations
	public enum EToolName { BallSD51SC, BallSD6, BallSD3, BallSD5SC,
							BallWorkingSD51SC, BallWorkingSD6, BallWorkingSD3, BallWorkingSD5SC,  
							None};
	public enum ESearchDirection { PosX, PosY, PosZ, NegX, NegY, NegZ };
	public enum EOscillationModes {sineMode, spiralMode, lissajousMode, simpleMode};
}
