package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class WireComponent extends GridComponent {

	private final boolean[] poweredFrom = new boolean[4];

	@Override
	boolean isPowering(int direction) {
		return powered && !poweredFrom[direction];
	}

	@Override
	boolean acceptsWireFrom(int direction) {
		return true;
	}

	@Override
	void tick(GridSquare gridSquare) {
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

		if(powered) {
			final GridSquare squareUp = gridSquare.getRelativeGridSquare(Direction.UP);
			//final GridComponent componentDown = gridSquare.getRelativeGridComponent(Direction.DOWN);
			if(squareUp != null) {
				if(squareUp.component != null) {
					if(!squareUp.component.powered || !squareUp.component.isPowering(Direction.DOWN)) {
						squareUp.component.tick(squareUp);
					}
				}
			}
			final GridSquare squareDown = gridSquare.getRelativeGridSquare(Direction.DOWN);
			//final GridComponent componentDown = gridSquare.getRelativeGridComponent(Direction.DOWN);
			if(squareDown != null) {
				if(squareDown.component != null) {
					if(!squareDown.component.powered || !squareDown.component.isPowering(Direction.UP)) {
						squareDown.component.tick(squareDown);
					}
				}
			}

			final GridSquare squareLeft = gridSquare.getRelativeGridSquare(Direction.LEFT);
			//final GridComponent componentRight = gridSquare.getRelativeGridComponent(Direction.RIGHT);
			if(squareLeft != null) {
				if(squareLeft.component != null) {
					if(!squareLeft.component.powered || !squareLeft.component.isPowering(Direction.RIGHT)) {
						squareLeft.component.tick(squareLeft);
					}
				}
			}

			final GridSquare squareRight = gridSquare.getRelativeGridSquare(Direction.RIGHT);
			//final GridComponent componentRight = gridSquare.getRelativeGridComponent(Direction.RIGHT);
			if(squareRight != null) {
				if(squareRight.component != null) {
					if(!squareRight.component.powered || !squareRight.component.isPowering(Direction.LEFT)) {
						squareRight.component.tick(squareRight);
					}
				}
			}
		} else {
			final GridSquare squareUp = gridSquare.getRelativeGridSquare(Direction.UP);
			//final GridComponent componentDown = gridSquare.getRelativeGridComponent(Direction.DOWN);
			if(squareUp != null) {
				if(squareUp.component != null) {
					if(squareUp.component.powered && !squareUp.component.isPowering(Direction.DOWN)) {
						squareUp.component.tick(squareUp);
					}
				}
			}

			final GridSquare squareDown = gridSquare.getRelativeGridSquare(Direction.DOWN);
			//final GridComponent componentDown = gridSquare.getRelativeGridComponent(Direction.DOWN);
			if(squareDown != null) {
				if(squareDown.component != null) {
					if(squareDown.component.powered && !squareDown.component.isPowering(Direction.UP)) {
						squareDown.component.tick(squareDown);
					}
				}
			}

			final GridSquare squareLeft = gridSquare.getRelativeGridSquare(Direction.LEFT);
			//final GridComponent componentRight = gridSquare.getRelativeGridComponent(Direction.RIGHT);
			if(squareLeft != null) {
				if(squareLeft.component != null) {
					if(squareLeft.component.powered && !squareLeft.component.isPowering(Direction.RIGHT)) {
						squareLeft.component.tick(squareLeft);
					}
				}
			}

			final GridSquare squareRight = gridSquare.getRelativeGridSquare(Direction.RIGHT);
			//final GridComponent componentDown = gridSquare.getRelativeGridComponent(Direction.DOWN);
			if(squareRight != null) {
				if(squareRight.component != null) {
					if(squareRight.component.powered && !squareRight.component.isPowering(Direction.LEFT)) {
						squareRight.component.tick(squareRight);
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
				if(component.acceptsWireFrom(Direction.getDirectionReversed(direction))) {
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
