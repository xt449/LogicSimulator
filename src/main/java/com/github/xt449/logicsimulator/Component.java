package com.github.xt449.logicsimulator;

import javafx.scene.image.ImageView;

import java.util.Collection;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
interface Component {

	boolean isReceivingPower(int direction);

	boolean isSendingPower(int direction);

	boolean hasInputFrom(int direction);

	boolean hasOutputTo(int direction);

	default boolean hasIO(int direction) {
		return hasInputFrom(direction) || hasOutputTo(direction);
	}

	void tick(ComponentContainer container);

	Collection<ImageView> getImages(ComponentContainer container);

	//void render(ComponentContainer container);
}
