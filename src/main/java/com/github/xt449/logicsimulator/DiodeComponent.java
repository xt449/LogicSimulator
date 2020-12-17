package com.github.xt449.logicsimulator;

import javafx.scene.image.ImageView;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class DiodeComponent implements DelayedComponent, DirectionalComponent {

	private boolean powered;
	private boolean nextPoweredState;

	private int direction;

	@Override
	public boolean isReceivingPower(int direction) {
		return false; // todo
	}

	@Override
	public boolean isSendingPower(int direction) {
		return direction == this.direction && powered;
	}

	@Override
	public boolean hasInputFrom(int direction) {
		return Direction.getDirectionReversed(this.direction) == direction;
	}

	@Override
	public boolean hasOutputTo(int direction) {
		return this.direction == direction;
	}

	@Override
	public void tick(ComponentContainer container) {
		powered = nextPoweredState;

		final ComponentContainer squareForward = container.getRelativeComponentContainer(direction);
		if(squareForward != null) {
			if(squareForward.component instanceof InstantComponent) {
				if(squareForward.component.hasInputFrom(Direction.getDirectionReversed(direction))) {
					squareForward.component.tick(squareForward);
				}
			}
		}
	}

	@Override
	public Collection<ImageView> getImages(ComponentContainer container) {
		final ArrayDeque<ImageView> queue = new ArrayDeque<>(3);

		final Component forwardComponent = container.getRelativeComponent(direction);
		final int directionReversed = Direction.getDirectionReversed(direction);
		final Component backwardComponent = container.getRelativeComponent(directionReversed);

		if(powered) {
			if(forwardComponent != null && forwardComponent.hasIO(direction)) {
				queue.add(new ImageView(Textures.getPoweredWire(direction)));
			}

			if(backwardComponent != null && backwardComponent.hasIO(directionReversed)) {
				queue.add(new ImageView(Textures.getPoweredWire(directionReversed)));
			}

			queue.add(new ImageView(Textures.getPoweredDiode(direction)));
		} else {
			if(forwardComponent != null && forwardComponent.hasIO(direction)) {
				queue.add(new ImageView(Textures.getWire(direction)));
			}

			if(backwardComponent != null && backwardComponent.hasIO(directionReversed)) {
				queue.add(new ImageView(Textures.getWire(directionReversed)));
			}

			queue.add(new ImageView(Textures.getDiode(direction)));
		}

		return queue;
	}

	@Override
	public void updateState(ComponentContainer container) {
		nextPoweredState = false;

		final Component component = container.getRelativeComponent(Direction.getDirectionReversed(direction));
		if(component != null) {
			if(component.isSendingPower(direction)) {
				nextPoweredState = true;
			}
		}
	}

	@Override
	public int getDirection() {
		return direction;
	}

	@Override
	public void rotate() {
		direction = Direction.rotate(direction);
	}
}
