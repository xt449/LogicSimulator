package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public abstract class GridComponent {

	protected boolean powered = false;

	abstract boolean isPowering(int direction);

	abstract boolean acceptsInputFrom(int direction);

	abstract boolean givesOutputTo(int direction);

	boolean hasIO(int direction) {
		return acceptsInputFrom(direction) || givesOutputTo(direction);
	}

	abstract void tick(GridSquare gridSquare);

	abstract void update(GridSquare gridSquare);

	abstract void render(GridSquare gridSquare);
}
