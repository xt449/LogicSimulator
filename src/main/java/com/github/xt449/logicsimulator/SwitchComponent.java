package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class SwitchComponent implements GridComponent, InteractableComponent {

	private boolean active;

	@Override
	public boolean isReceivingPower(int direction) {
		return false;
	}

	@Override
	public boolean isSendingPower(int direction) {
		return active;
	}

	@Override
	public boolean hasInputFrom(int direction) {
		return false;
	}

	@Override
	public boolean hasOutputTo(int direction) {
		return true;
	}

	@Override
	public void tick(GridComponentContainer container) {
		// nothing to update
	}

	@Override
	public void render(GridComponentContainer container) {
		LogicSimulator.instance.prepareDrawTexture(active ? Textures.SWITCH_ON : Textures.SWITCH_OFF);
		LogicSimulator.instance.drawTextureGridPosition(container.x, container.y);
	}

	@Override
	public void interact() {
		active = !active;
	}
}
