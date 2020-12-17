package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
interface GridComponentTest {

	boolean hasInputFrom(int direction);

	boolean hasOutputTo(int direction);

	default boolean hasIO(int direction) {
		return hasInputFrom(direction) || hasOutputTo(direction);
	}

	/**
	 * @return true if state changed
	 */
	boolean tick(GridComponentContainer container);

	void tickEnd();

	//void tick(GridComponentContainer container);

	State getState();

	void render(GridComponentContainer container);

	interface State {
		boolean isReceivingPower(int direction);

		boolean isSendingPower(int direction);
	}
}
