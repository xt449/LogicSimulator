package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class WireComponent extends GridComponent {

	private final boolean[] poweredFrom = new boolean[4];

	@Override
	boolean isPowering(int direction) {
		return powered && !poweredFrom[direction];
	}

	@Override
	boolean acceptsInputFrom(int direction) {
		return true;
	}

	@Override
	boolean givesOutputTo(int direction) {
		return true;
	}

	@Override
	void tick(GridSquare gridSquare) {
		// Updates mid-tick through update
	}

	@Override
	void update(GridSquare gridSquare) {
		powered = false;
		poweredFrom[0] = false;
		poweredFrom[1] = false;
		poweredFrom[2] = false;
		poweredFrom[3] = false;

		for(int direction = 0; direction < 4; direction++) {
			final GridComponent component = gridSquare.getRelativeGridComponent(direction);
			if(component != null) {
				if(component.isPowering(Direction.getDirectionReversed(direction))) {
					powered = true;
					poweredFrom[direction] = true;
				}
			}
		}

		final GridSquare squareUp = gridSquare.getRelativeGridSquare(Direction.UP);
		final GridSquare squareDown = gridSquare.getRelativeGridSquare(Direction.DOWN);
		final GridSquare squareLeft = gridSquare.getRelativeGridSquare(Direction.LEFT);
		final GridSquare squareRight = gridSquare.getRelativeGridSquare(Direction.RIGHT);
		if(powered) {
			if(squareUp != null) {
				if(squareUp.component != null) {
					//if(!squareUp.component.powered || !squareUp.component.isPowering(Direction.DOWN)) {
					if(!squareUp.component.powered && squareUp.component.acceptsInputFrom(Direction.DOWN)) {
						squareUp.component.update(squareUp);
					}
				}
			}
			if(squareDown != null) {
				if(squareDown.component != null) {
					//if(!squareDown.component.powered || !squareDown.component.isPowering(Direction.UP)) {
					if(!squareDown.component.powered && squareDown.component.acceptsInputFrom(Direction.UP)) {
						squareDown.component.update(squareDown);
					}
				}
			}

			if(squareLeft != null) {
				if(squareLeft.component != null) {
					//if(!squareLeft.component.powered || !squareLeft.component.isPowering(Direction.RIGHT)) {
					if(!squareLeft.component.powered && squareLeft.component.acceptsInputFrom(Direction.RIGHT)) {
						squareLeft.component.update(squareLeft);
					}
				}
			}

			if(squareRight != null) {
				if(squareRight.component != null) {
					//if(!squareRight.component.powered || !squareRight.component.isPowering(Direction.LEFT)) {
					if(!squareRight.component.powered && squareRight.component.acceptsInputFrom(Direction.LEFT)) {
						squareRight.component.update(squareRight);
					}
				}
			}
		} else {
			if(squareUp != null) {
				if(squareUp.component != null) {
					if(squareUp.component.powered && squareUp.component.acceptsInputFrom(Direction.DOWN)) {
						squareUp.component.update(squareUp);
					}
				}
			}

			if(squareDown != null) {
				if(squareDown.component != null) {
					if(squareDown.component.powered && squareDown.component.acceptsInputFrom(Direction.UP)) {
						squareDown.component.update(squareDown);
					}
				}
			}

			if(squareLeft != null) {
				if(squareLeft.component != null) {
					if(squareLeft.component.powered && squareLeft.component.acceptsInputFrom(Direction.RIGHT)) {
						squareLeft.component.update(squareLeft);
					}
				}
			}

			if(squareRight != null) {
				if(squareRight.component != null) {
					if(squareRight.component.powered && squareRight.component.acceptsInputFrom(Direction.LEFT)) {
						squareRight.component.update(squareRight);
					}
				}
			}
		}
	}

	@Override
	void render(GridSquare gridSquare) {
		for(int direction = 0; direction < 4; direction++) {
			final GridComponent component = gridSquare.getRelativeGridComponent(direction);
			if(component != null) {
				if(component.hasIO(Direction.getDirectionReversed(direction))) {
					LogicSimulator.instance.prepareDrawTexture(powered ? Texture.getPoweredWire(direction) : Texture.getWire(direction));
					LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
				}
			}
		}

		LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_CENTER_POWERED : Texture.WIRE_CENTER);
		LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);

		for(int direction = 0; direction < poweredFrom.length; direction++) {
			if(poweredFrom[direction]) {
				LogicSimulator.instance.prepareDrawTexture(Texture.getArrow(Direction.getDirectionReversed(direction)));
				LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
			}
		}
	}
}
