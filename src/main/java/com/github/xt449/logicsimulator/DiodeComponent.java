package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class DiodeComponent extends DirectionalGridComponent {

	public DiodeComponent() {
		super();
	}

	public DiodeComponent(int direction) {
		super(direction);
	}

	@Override
	boolean isPowering(int direction) {
		return direction == this.direction && powered;
	}

	@Override
	boolean acceptsInputFrom(int direction) {
		return Direction.getDirectionReversed(this.direction) == direction;
	}

	@Override
	boolean givesOutputTo(int direction) {
		return this.direction == direction;
	}

	private boolean nextPoweredState;

	@Override
	void tick(GridSquare gridSquare) {
		powered = nextPoweredState;
	}

	@Override
	void update(GridSquare gridSquare) {
		nextPoweredState = false;
		//powered = false;

		final GridComponent component = gridSquare.getRelativeGridComponent(Direction.getDirectionReversed(direction));
		if(component != null) {
			if(component.isPowering(direction)) {
				nextPoweredState = true;
			}
		}

//		if(powered) {
//			final GridComponent forwardComponent = gridSquare.getRelativeGridComponent(direction);
//			if(forwardComponent != null) {
//				forwardComponent.power(Direction.getDirectionReversed(direction));
//			}
//		}

		final GridSquare squareForward = gridSquare.getRelativeGridSquare(direction);
		if(nextPoweredState) {
			if(squareForward != null) {
				if(squareForward.component != null) {
					if(!squareForward.component.powered || !squareForward.component.isPowering(Direction.getDirectionReversed(direction))) {
						squareForward.component.update(squareForward);
					}
				}
			}
		} else {
			if(squareForward != null) {
				if(squareForward.component != null) {
					if(squareForward.component.powered && !squareForward.component.isPowering(Direction.getDirectionReversed(direction))) {
						squareForward.component.update(squareForward);
					}
				}
			}
		}
	}

	@Override
	void render(GridSquare gridSquare) {
		final GridComponent forwardComponent = gridSquare.getRelativeGridComponent(direction);
		final int directionReversed = Direction.getDirectionReversed(direction);
		final GridComponent backwardComponent = gridSquare.getRelativeGridComponent(directionReversed);

		if(powered) {
			if(forwardComponent != null && forwardComponent.hasIO(direction)) {
				LogicSimulator.instance.prepareDrawTexture(Texture.getPoweredWire(direction));
				LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
			}

			if(backwardComponent != null && backwardComponent.hasIO(directionReversed)) {
				LogicSimulator.instance.prepareDrawTexture(Texture.getPoweredWire(directionReversed));
				LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
			}

			LogicSimulator.instance.prepareDrawTexture(Texture.getPoweredDiode(direction));
		} else {
			if(forwardComponent != null && forwardComponent.hasIO(direction)) {
				LogicSimulator.instance.prepareDrawTexture(Texture.getWire(direction));
				LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
			}

			if(backwardComponent != null && backwardComponent.hasIO(directionReversed)) {
				LogicSimulator.instance.prepareDrawTexture(Texture.getWire(directionReversed));
				LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
			}

			LogicSimulator.instance.prepareDrawTexture(Texture.getDiode(direction));
		}
		LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
	}
}
