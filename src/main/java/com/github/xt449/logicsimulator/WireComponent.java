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
			/*final GridSquare square = gridSquare.getRelativeGridSquare(poweredFromDirection);
			if(square != null) {
				if(square.component != null) {
					if(square.component instanceof WireComponent) {
						if(square.component.powered) {
							powered = true;
						}
					} else if(square.component instanceof InverterComponent) {
						if(!square.component.powered && (poweredFromDirection == Direction.getDirectionReversed(((InverterComponent) square.component).getDirection()))) {
							powered = true;
						}
					}
				}
			}*/
			boolean powered = false;
			for(int direction = 0; direction < 4; direction++) {
				final GridSquare square = gridSquare.getRelativeGridSquare(direction);
				if(square != null) {
					if(square.component != null) {
						if(square.component instanceof WireComponent) {
							if(square.component.powered) {
								powered = true;
							}
						} else if(square.component instanceof InverterComponent) {
							if(!square.component.powered && (direction == Direction.getDirectionReversed(((InverterComponent) square.component).getDirection()))) {
								powered = true;
							}
						}
					}
				}
			}
			if(!powered) {
				this.powered = false;
				return;
			}

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

		// TODO - LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_CENTER_POWERED : Texture.WIRE_CENTER);
		LogicSimulator.instance.prepareDrawTexture(powered ? Texture.getPoweredInverter(Direction.getDirectionReversed(poweredFromDirection)) : Texture.getInverter(Direction.getDirectionReversed(poweredFromDirection)));
		LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
	}
}
