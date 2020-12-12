package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class SwitchComponent extends GridComponent implements InteractableComponent {

	public SwitchComponent() {

	}

	@Override
	boolean isPowering(int direction) {
		return powered;
	}

	@Override
	boolean acceptsInputFrom(int direction) {
		return false;
	}

	@Override
	boolean givesOutputTo(int direction) {
		return true;
	}

	@Override
	void tick(GridSquare gridSquare) {
		// nothing to tick
	}

	@Override
	void update(GridSquare gridSquare) {
		// nothing to update
	}

	@Override
	void render(GridSquare gridSquare) {
		LogicSimulator.instance.prepareDrawTexture(powered ? Texture.SWITCH_ON : Texture.SWITCH_OFF);
		LogicSimulator.instance.drawTextureGridPosition(gridSquare.x, gridSquare.y);
	}

	@Override
	public void interact() {
		powered = !powered;
	}
}
