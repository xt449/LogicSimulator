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

//		if(powered) {
//			for(int direction = 0; direction < 4; direction++) {
//				if(direction == poweredFromDirection) {
//					continue;
//				}
//				final GridSquare square = gridSquare.getRelativeGridSquare(direction);
//				if(square != null) {
//					if(square.component != null) {
//						square.power(Direction.getDirectionReversed(direction));
//					}
//				}
//			}
//		}
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
