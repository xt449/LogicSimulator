package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public interface DirectionalComponent extends InteractableComponent {
	default void interact() {
		rotate();
	}

	int getDirection();

	void rotate();
}
