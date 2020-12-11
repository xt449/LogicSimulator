package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public abstract class GridComponent {

	protected boolean powered = false;

	/*protected void setPowered(boolean powered) {
		this.powered = powered;
	}*/

	abstract boolean isPowering(int direction);

	abstract boolean acceptsWireFrom(int direction);

	abstract void tick(GridSquare gridSquare);

	abstract void render(GridSquare gridSquare);
}
