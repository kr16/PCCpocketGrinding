package modules;

import modules.Common.ECalcDirection;

import com.kuka.roboticsAPI.geometricModel.Frame;

public class CouponCalc {
	
	public Frame calculateXYpos(int row, int column, Frame refrencePos, double rowOffset, double columnOffset, ECalcDirection direction) {
		double xOffset, yOffset;
		switch (direction) {
		case XisRow:
			//rows 5 and 6 offset original position
			if (row == 5 || row == 6) {
				refrencePos.setX(refrencePos.getX() + 104.5);
				refrencePos.setY(refrencePos.getY() - 17.5);
				row = row - 4;
			}
			
			xOffset = ((row-1)*rowOffset);
			//stupid fix for uneven row distance on coupons
			if (row==4) {
				System.out.println("Row 4: 1.5mm added on X !!!");
				xOffset = xOffset + 1.5;
			}
			yOffset = ((column-1)*columnOffset);
			break;
		case YisRow:
			yOffset = ((row-1)*rowOffset);
			xOffset = ((column-1)*columnOffset);
			break;		
		default:
			xOffset = yOffset = 0;
			break;
		}
		refrencePos.setX(refrencePos.getX() + xOffset);
		refrencePos.setY(refrencePos.getY() + yOffset);
		
		return refrencePos;
	}	
}
