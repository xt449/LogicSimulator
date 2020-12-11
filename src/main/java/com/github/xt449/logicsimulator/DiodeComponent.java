package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class DiodeComponent extends DirectionalGridComponent {

	public DiodeComponent() {
		super();
	}

	public DiodeComponent(int direction) {
		super(direction);
	}

	@Override
	boolean redirectsWireFrom(int direction) {
		return this.direction == direction || Direction.getDirectionReversed(this.direction) == direction;
	}

	@Override
	void power(int direction) {
		if(Direction.getDirectionReversed(direction) == this.direction) {
			powered = true;
		}
	}

	@Override
	void tick(GridSquare gridSquare) {
		if(powered) {
			final GridSquare backwardSquare = gridSquare.getRelativeGridSquare(Direction.getDirectionReversed(direction));
			if(backwardSquare == null || backwardSquare.component == null || !backwardSquare.component.powered) {
				powered = false;
				return;
			}
		}

		if(!powered) {
			final GridSquare forwardSquare = gridSquare.getRelativeGridSquare(direction);
			if(forwardSquare != null) {
				forwardSquare.power(Direction.getDirectionReversed(direction));
			}
		}
	}

	@Override
	void render(GridSquare gridSquare) {
		final GridComponent forwardComponent = gridSquare.getRelativeGridComponent(direction);
		if(forwardComponent != null && forwardComponent.redirectsWireFrom(direction)) {
			LogicSimulator.instance.prepareDrawTexture(!powered ? Texture.getPoweredWire(direction) : Texture.getWire(direction));
			LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
		}

		final int directionReversed = Direction.getDirectionReversed(direction);
		final GridComponent backwardComponent = gridSquare.getRelativeGridComponent(directionReversed);
		if(backwardComponent != null && backwardComponent.redirectsWireFrom(directionReversed)) {
			LogicSimulator.instance.prepareDrawTexture(powered ? Texture.getPoweredWire(directionReversed) : Texture.getWire(directionReversed));
			LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
		}

		LogicSimulator.instance.prepareDrawTexture(powered ? Texture.getPoweredDiode(direction) : Texture.getDiode(direction));
		LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
	}
}
