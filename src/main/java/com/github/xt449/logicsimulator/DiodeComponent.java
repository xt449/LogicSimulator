package com.github.xt449.logicsimulator;

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
	public void tick(GridComponentContainer container) {
		powered = nextPoweredState;

		final GridComponentContainer squareForward = container.getRelativeGridSquare(direction);
		if(squareForward != null) {
			if(squareForward.component instanceof InstantComponent) {
				if(squareForward.component.hasInputFrom(Direction.getDirectionReversed(direction))) {
					squareForward.component.tick(squareForward);
				}
			}
		}
	}

	@Override
	public void updateState(GridComponentContainer container) {
		nextPoweredState = false;

		final GridComponent component = container.getRelativeGridComponent(Direction.getDirectionReversed(direction));
		if(component != null) {
			if(component.isSendingPower(direction)) {
				nextPoweredState = true;
			}
		}
	}

	@Override
	public void render(GridComponentContainer container) {
		final GridComponent forwardComponent = container.getRelativeGridComponent(direction);
		final int directionReversed = Direction.getDirectionReversed(direction);
		final GridComponent backwardComponent = container.getRelativeGridComponent(directionReversed);

		if(powered) {
			if(forwardComponent != null && forwardComponent.hasIO(direction)) {
				LogicSimulator.instance.prepareDrawTexture(Texture.getPoweredWire(direction));
				LogicSimulator.instance.drawTextureGridPosition(container.x, container.y);
			}

			if(backwardComponent != null && backwardComponent.hasIO(directionReversed)) {
				LogicSimulator.instance.prepareDrawTexture(Texture.getPoweredWire(directionReversed));
				LogicSimulator.instance.drawTextureGridPosition(container.x, container.y);
			}

			LogicSimulator.instance.prepareDrawTexture(Texture.getPoweredDiode(direction));
		} else {
			if(forwardComponent != null && forwardComponent.hasIO(direction)) {
				LogicSimulator.instance.prepareDrawTexture(Texture.getWire(direction));
				LogicSimulator.instance.drawTextureGridPosition(container.x, container.y);
			}

			if(backwardComponent != null && backwardComponent.hasIO(directionReversed)) {
				LogicSimulator.instance.prepareDrawTexture(Texture.getWire(directionReversed));
				LogicSimulator.instance.drawTextureGridPosition(container.x, container.y);
			}

			LogicSimulator.instance.prepareDrawTexture(Texture.getDiode(direction));
		}
		LogicSimulator.instance.drawTextureGridPosition(container.x, container.y);
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
