package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class WireComponent extends GridComponent {

	private int poweredFromDirection;

	@Override
	boolean redirectsWireFrom(int direction) {
		return true;
	}

	@Override
	void power(int direction) {
		poweredFromDirection = direction;
		powered = true;
	}

	@Override
	void tick(GridSquare gridSquare) {
		if(powered) {
			//int backTrackCount = 0;
			GridSquare currentSquare = gridSquare;
			GridSquare currentSource;
			//do {
				currentSource = currentSquare.getRelativeGridSquare(poweredFromDirection);
				if(currentSource == null) {
					currentSquare.component.powered = false;
					//break;
				} else {
					if(currentSource.component == null) {
						currentSquare.component.powered = false;
						//break;
					} else {
						if(currentSource.component instanceof WireComponent) {
							if(!currentSource.component.powered) {
								currentSquare.component.powered = false;
								//break;
							}
						} else if(currentSource.component instanceof InverterComponent) {
							currentSquare.component.powered = !currentSource.component.powered && (((WireComponent) currentSquare.component).poweredFromDirection == Direction.getDirectionReversed(((InverterComponent) currentSource.component).getDirection()));
							//break;
						}
					}
				}
				currentSquare = currentSource;
			//} while(++backTrackCount < 32);

			//final GridSquare testSquare = gridSquare.getRelativeGridSquare(poweredFromDirection);
			//final boolean powered = (testSquare != null) && (testSquare.component != null) && ((testSquare.component instanceof WireComponent) ? testSquare.component.powered : ((testSquare.component instanceof InverterComponent) && !testSquare.component.powered && (poweredFromDirection == Direction.getDirectionReversed(((InverterComponent) testSquare.component).getDirection()))));

			for(int direction = 0; direction < 4; direction++) {
				if(direction == poweredFromDirection) {
					continue;
				}
				final GridSquare square = gridSquare.getRelativeGridSquare(direction);
				if(square != null) {
					if(square.component != null) {
						square.power(Direction.getDirectionReversed(direction));
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
				if(component.redirectsWireFrom(Direction.getDirectionReversed(direction))) {
					LogicSimulator.instance.prepareDrawTexture(powered ? Texture.getPoweredWire(direction) : Texture.getWire(direction));
					LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
				}
			}
		}

		LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_CENTER_POWERED : Texture.WIRE_CENTER);
		LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);

		LogicSimulator.instance.prepareDrawTexture(powered ? Texture.getArrow(Direction.getDirectionReversed(poweredFromDirection)) : Texture.getArrow(Direction.getDirectionReversed(poweredFromDirection)));
		LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
	}
}
