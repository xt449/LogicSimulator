package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class GridSquare {

	private final int x;
	private final int y;

	int component = GridComponent.NONE;
	boolean powered = false;

	public GridSquare(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void rotateComponent() {
		if(component < 4) {
			return;
		}

		if(++component % 4 == 0) {
			component -= 4;
		}
	}

	private void update() {
		// TODO
	}

	public void removeComponent() {
		component = GridComponent.NONE;
	}

	public void setWireComponent() {
		component = GridComponent.WIRE;
	}

	public void setInverterComponent() {
		component = GridComponent.INVERTER_UP;
	}

	public void tick() {
         //
	}

	public void render() {
		if(component == GridComponent.NONE) {
			return;
		}

		if(LogicSimulator.instance.getGridSquareComponent(x, y - 1) != GridComponent.NONE) {
			LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_UP_POWERED : Texture.WIRE_UP);
			LogicSimulator.instance.drawTextureGridPosition(x, y);
		}
		if(LogicSimulator.instance.getGridSquareComponent(x, y + 1) != GridComponent.NONE) {
			LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_DOWN_POWERED : Texture.WIRE_DOWN);
			LogicSimulator.instance.drawTextureGridPosition(x, y);
		}
		if(LogicSimulator.instance.getGridSquareComponent(x - 1, y) != GridComponent.NONE) {
			LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_LEFT_POWERED : Texture.WIRE_LEFT);
			LogicSimulator.instance.drawTextureGridPosition(x, y);
		}
		if(LogicSimulator.instance.getGridSquareComponent(x + 1, y) != GridComponent.NONE) {
			LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_RIGHT_POWERED : Texture.WIRE_RIGHT);
			LogicSimulator.instance.drawTextureGridPosition(x, y);
		}

		switch(component) {
			case GridComponent.WIRE: {
				LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_CENTER_POWERED : Texture.WIRE_CENTER);
				break;
			}
			case GridComponent.INVERTER_UP: {
				LogicSimulator.instance.prepareDrawTexture(powered ? Texture.INVERTER_UP_POWERED : Texture.INVERTER_UP);
				break;
			}
			case GridComponent.INVERTER_DOWN: {
				LogicSimulator.instance.prepareDrawTexture(powered ? Texture.INVERTER_DOWN_POWERED : Texture.INVERTER_DOWN);
				break;
			}
			case GridComponent.INVERTER_LEFT: {
				LogicSimulator.instance.prepareDrawTexture(powered ? Texture.INVERTER_LEFT_POWERED : Texture.INVERTER_LEFT);
				break;
			}
			case GridComponent.INVERTER_RIGHT: {
				LogicSimulator.instance.prepareDrawTexture(powered ? Texture.INVERTER_RIGHT_POWERED : Texture.INVERTER_RIGHT);
				break;
			}
			default: return;
		}

		LogicSimulator.instance.drawTextureGridPosition(x, y);
	}
}
