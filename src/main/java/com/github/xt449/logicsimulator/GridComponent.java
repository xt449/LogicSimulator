package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
interface GridComponent {

	boolean isReceivingPower(int direction);

	boolean isSendingPower(int direction);

	boolean hasInputFrom(int direction);

	boolean hasOutputTo(int direction);

	default boolean hasIO(int direction) {
		return hasInputFrom(direction) || hasOutputTo(direction);
	}

	//abstract void preTick(GridSquare gridSquare);

	void tick(GridComponentContainer container);

	void render(GridComponentContainer container);
}
