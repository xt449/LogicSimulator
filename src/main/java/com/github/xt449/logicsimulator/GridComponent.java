package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public abstract class GridComponent {

	boolean powered = false;

	boolean redirectsWireFrom(int direction) {
		return false;
	}

	abstract void power(int direction);

	abstract void tick(GridSquare gridSquare);

	abstract void render(GridSquare gridSquare);
}
