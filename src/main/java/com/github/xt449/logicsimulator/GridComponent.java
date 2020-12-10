package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public abstract class GridComponent {

	boolean powered = false;

	/*public static final int NONE =           0b0000;
	public static final int WIRE =           0b0001;

	public static final int INVERTER_UP =    0b0100;
	public static final int INVERTER_DOWN =  0b0101;
	public static final int INVERTER_LEFT =  0b0110;
	public static final int INVERTER_RIGHT = 0b0111;

	public static boolean isInverter(int component) {
		return component >= INVERTER_UP && component <= INVERTER_RIGHT;
	}*/

	//public static final GridSquare NONE = null;

	boolean redirectsWireFrom(int direction) {
		return false;
	}
}
