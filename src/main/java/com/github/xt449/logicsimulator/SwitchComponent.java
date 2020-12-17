package com.github.xt449.logicsimulator;

import javafx.scene.image.ImageView;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class SwitchComponent implements Component, InteractableComponent {

	private boolean active;

	@Override
	public boolean isReceivingPower(int direction) {
		return false;
	}

	@Override
	public boolean isSendingPower(int direction) {
		return active;
	}

	@Override
	public boolean hasInputFrom(int direction) {
		return false;
	}

	@Override
	public boolean hasOutputTo(int direction) {
		return true;
	}

	@Override
	public void tick(ComponentContainer container) {
		// nothing to update
	}

	@Override
	public Collection<ImageView> getImages(ComponentContainer componentContainer) {
		final ArrayDeque<ImageView> queue = new ArrayDeque<>(1);
		queue.add(new ImageView(active ? Textures.SWITCH_ON : Textures.SWITCH_OFF));
		return queue;
	}

	@Override
	public void interact() {
		active = !active;
	}
}
