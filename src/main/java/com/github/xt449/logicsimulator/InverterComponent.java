package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class InverterComponent extends DiodeComponent {

	public InverterComponent() {
		super();
	}

	public InverterComponent(int direction) {
		super(direction);
	}

	@Override
	boolean isPowering(int direction) {
		return direction == this.direction && !powered;
	}

	private boolean nextPoweredState;

	@Override
	void tick(GridSquare gridSquare) {
		powered = nextPoweredState;
	}

	@Override
	void update(GridSquare gridSquare) {
		nextPoweredState = false;

		final GridComponent component = gridSquare.getRelativeGridComponent(Direction.getDirectionReversed(direction));
		if(component != null) {
			if(component.isPowering(direction)) {
				nextPoweredState = true;
			}
		}

		final GridSquare squareForward = gridSquare.getRelativeGridSquare(direction);
		if(powered) {
			if(squareForward != null) {
				if(squareForward.component != null) {
					if(squareForward.component.powered && squareForward.component.acceptsInputFrom(Direction.getDirectionReversed(direction))) {
						squareForward.component.update(squareForward);
					}
				}
			}
		} else {
			if(squareForward != null) {
				if(squareForward.component != null) {
					if(!squareForward.component.powered && squareForward.component.acceptsInputFrom(Direction.getDirectionReversed(direction))) {
						squareForward.component.update(squareForward);
					}
				}
			}
		}
	}

	@Override
	void render(GridSquare gridSquare) {
		final GridComponent forwardComponent = gridSquare.getRelativeGridComponent(direction);
		if(forwardComponent != null && forwardComponent.hasIO(direction)) {
			LogicSimulator.instance.prepareDrawTexture(!powered ? Texture.getPoweredWire(direction) : Texture.getWire(direction));
			LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
		}

		final int directionReversed = Direction.getDirectionReversed(direction);
		final GridComponent backwardComponent = gridSquare.getRelativeGridComponent(directionReversed);
		if(backwardComponent != null && backwardComponent.hasIO(directionReversed)) {
			LogicSimulator.instance.prepareDrawTexture(powered ? Texture.getPoweredWire(directionReversed) : Texture.getWire(directionReversed));
			LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
		}

		LogicSimulator.instance.prepareDrawTexture(powered ? Texture.getPoweredInverter(direction) : Texture.getInverter(direction));
		LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
	}
}
