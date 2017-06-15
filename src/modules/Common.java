package modules;

import com.kuka.roboticsAPI.geometricModel.Frame;

/**
 * Implementation of a cyclic background task.
 * Provides management of the part feeding panoply
 */
public interface Common {
   
	// Enumerations
	public enum EPart { Unknown, None, ECROU, RIVET, DOUILLE, BAGUE };
	public enum EDirection { positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ };
	public enum EJoint {J1, J2, J3, J4, J5, J7, J8};
	public enum searchDir { PosX, PosY, PosZ, NegX, NegY, NegZ };
	public enum EHilokName {HL70_10, HL70_12, HL70_08, HL70_06, HL100Foo_Nut};
	public enum EPointType {LIN, PTP, LINC, PTPC};
	public enum EProcessType {NOP, NUTRUN, INSPECTION};
	public enum EPointStatus {OPEN, COMPLETE, ERROR};
	public enum EFileExtension {txt, xml, csv};
	public enum ECalcDirection {XisRow, YisRow};
	public enum EHotDotCouponStates {Empty, Smudged, Skived, Scaned, Skip, Error, EmptyScaned};
	public enum ECouponSectionName {Coupon14, Coupon56, Coupon79}
	public enum ECognexTrigger {SW8, SE8, NULL};
	public enum ECognexCommand {GV, SF, NULL};
	
    // Function results
	public static final int ciFunctionInProgress        = -1; 
	public static final int ciFunctionInactive          = 0; 
	public static final int ciFunctionCompletedOK       = 1; 
	public static final int ciFunctionCompletedKO       = 2; 
	public static final int ciPartPresenceInconsistency = 10; 
	public static final int ciUnknownPartName           = 11; 
	public static final int ciUnknownDirectionName      = 12; 
	public static final int ciDatumPartNotDetected      = 13; 
	public static final int ciEcrouPartDetected			= 14;
	public static final int ciEcrouPartNotDetected		= 15;
	public static final int ciPartMismatched			= 16;
	public static final int ciFinalPositionReached		= 17;
	public static final int ciHomingSequenceFailed		= 18;
	public static final int ciEnablingSequenceFailed	= 19;
	public static final int ciMoveInProgress		    = 20;
	public static final int ciMoveCompletedOK		    = 21;
	public static final int ciMoveSequenceFailed	    = 22;
	public static final int ciMoveAborted			    = 23;
	
	// Function results KCT
	public static final int ciNutCompleted				= 50;
	public static final int ciNutFailed					= 51;
	public static final int ciNutBreakForceFailed		= 52;
	public static final int ciNutReleaseForceFailed		= 53;
	public static final int ciNutAntiCrossFailed		= 54;
	
	 
	// Geometric constants
	public final Frame nullFrame = new Frame(0,0,0,0,0,0);
    
}

