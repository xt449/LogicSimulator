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

	public void update() {
		if(powered) {
			// TODO
			if(LogicSimulator.instance.getGridSquareComponent(x, y - 1) != GridComponent.NONE) {
				final GridSquare square = LogicSimulator.instance.getGridSquare(x, y - 1);
				if(square != null) {
					if(!square.powered) {
						square.powered = true;
						square.update();
					}
				}
			}
			if(LogicSimulator.instance.getGridSquareComponent(x, y + 1) != GridComponent.NONE) {
				final GridSquare square = LogicSimulator.instance.getGridSquare(x, y + 1);
				if(square != null) {
					if(!square.powered) {
						square.powered = true;
						square.update();
					}
				}
			}
			if(LogicSimulator.instance.getGridSquareComponent(x - 1, y) != GridComponent.NONE) {
				final GridSquare square = LogicSimulator.instance.getGridSquare(x - 1, y);
				if(square != null) {
					if(!square.powered) {
						square.powered = true;
						square.update();
					}
				}
			}
			if(LogicSimulator.instance.getGridSquareComponent(x + 1, y) != GridComponent.NONE) {
				final GridSquare square = LogicSimulator.instance.getGridSquare(x + 1, y);
				if(square != null) {
					if(!square.powered) {
						square.powered = true;
						square.update();
					}
				}
			}
		} else {
			if(GridComponent.isInverter(component)) {
				GridSquare forwardSquare = null;
				switch(component) {
					case GridComponent.INVERTER_UP: {
						forwardSquare = LogicSimulator.instance.getGridSquare(x, y - 1);
						break;
					}
					case GridComponent.INVERTER_DOWN: {
						forwardSquare = LogicSimulator.instance.getGridSquare(x, y + 1);
						break;
					}
					case GridComponent.INVERTER_LEFT: {
						forwardSquare = LogicSimulator.instance.getGridSquare(x - 1, y);
						break;
					}
					case GridComponent.INVERTER_RIGHT: {
						forwardSquare = LogicSimulator.instance.getGridSquare(x + 1, y);
						break;
					}
				}

				if(forwardSquare != null) {
					if(!forwardSquare.powered) {
						forwardSquare.powered = true;
						forwardSquare.update();
					}
				}
			}
		}
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
         powered = false;
         update();
	}

	public void render() {
		if(component == GridComponent.NONE) {
			return;
		}

		if(component == GridComponent.WIRE) {
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

			LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_CENTER_POWERED : Texture.WIRE_CENTER);
			LogicSimulator.instance.drawTextureGridPosition(x, y);
			return;
		}

		Texture topTexture = null;
		switch(component) {
			case GridComponent.INVERTER_UP: {
				topTexture = powered ? Texture.INVERTER_UP_POWERED : Texture.INVERTER_UP;
				break;
			}
			case GridComponent.INVERTER_DOWN: {
				topTexture = powered ? Texture.INVERTER_DOWN_POWERED : Texture.INVERTER_DOWN;
				break;
			}
			case GridComponent.INVERTER_LEFT: {
				topTexture = powered ? Texture.INVERTER_LEFT_POWERED : Texture.INVERTER_LEFT;
				break;
			}
			case GridComponent.INVERTER_RIGHT: {
				topTexture = powered ? Texture.INVERTER_RIGHT_POWERED : Texture.INVERTER_RIGHT;
				break;
			}
		}

		if(LogicSimulator.instance.getGridSquareComponent(x, y - 1) == GridComponent.WIRE) {
			LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_UP_POWERED : Texture.WIRE_UP);
			LogicSimulator.instance.drawTextureGridPosition(x, y);
		}
		if(LogicSimulator.instance.getGridSquareComponent(x, y + 1) == GridComponent.WIRE) {
			LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_DOWN_POWERED : Texture.WIRE_DOWN);
			LogicSimulator.instance.drawTextureGridPosition(x, y);
		}
		if(LogicSimulator.instance.getGridSquareComponent(x - 1, y) == GridComponent.WIRE) {
			LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_LEFT_POWERED : Texture.WIRE_LEFT);
			LogicSimulator.instance.drawTextureGridPosition(x, y);
		}
		if(LogicSimulator.instance.getGridSquareComponent(x + 1, y) == GridComponent.WIRE) {
			LogicSimulator.instance.prepareDrawTexture(powered ? Texture.WIRE_RIGHT_POWERED : Texture.WIRE_RIGHT);
			LogicSimulator.instance.drawTextureGridPosition(x, y);
		}

		if(topTexture != null) {
			LogicSimulator.instance.prepareDrawTexture(topTexture);
			LogicSimulator.instance.drawTextureGridPosition(x, y);
		}
	}
}
