package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class GridSquare {

	private final int x;
	private final int y;

	GridComponent component = null;

	public GridSquare(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void update() {
		if(component == null) {
			return;
		}

		if(component.powered) {
			// TODO
			if(getGridSquareComponentRelative(x, y, Direction.UP) != null) {
				final GridSquare square = getGridSquareRelative(x, y, Direction.UP);
				if(square != null) {
					if(!square.component.powered) {
						square.component.powered = true;
						square.update();
					}
				}
			}
			if(getGridSquareComponentRelative(x, y, Direction.DOWN) != null) {
				final GridSquare square = getGridSquareRelative(x, y, Direction.DOWN);
				if(square != null) {
					if(!square.component.powered) {
						square.component.powered = true;
						square.update();
					}
				}
			}
			if(getGridSquareComponentRelative(x, y, Direction.LEFT) != null) {
				final GridSquare square = getGridSquareRelative(x, y, Direction.LEFT);
				if(square != null) {
					if(!square.component.powered) {
						square.component.powered = true;
						square.update();
					}
				}
			}
			if(getGridSquareComponentRelative(x, y, Direction.RIGHT) != null) {
				final GridSquare square = getGridSquareRelative(x, y, Direction.RIGHT);
				if(square != null) {
					if(!square.component.powered) {
						square.component.powered = true;
						square.update();
					}
				}
			}
		} else {
			if(component instanceof InverterComponent) {
				final GridSquare forwardSquare = getGridSquareRelative(x, y, ((InverterComponent) component).getDirection());
				if(forwardSquare != null) {
					if(!forwardSquare.component.powered) {
						forwardSquare.component.powered = true;
						forwardSquare.update();
					}
				}
			}
		}
	}

	/*public void removeComponent() {
		component = null;
	}

	public void setWireComponent() {
		component = GridComponent.WIRE;
	}

	public void setInverterComponent() {
		component = GridComponent.INVERTER_UP;
	}*/

	public void tick() {
         component.powered = false;
         update();
	}

	public void render() {
		if(component == null) {
			return;
		}

		if(component instanceof WireComponent) {
			if(getGridSquareComponentRelative(x, y, Direction.UP) != null) {
				LogicSimulator.instance.prepareDrawTexture(component.powered ? Texture.WIRE_UP_POWERED : Texture.WIRE_UP);
				LogicSimulator.instance.drawTextureGridPosition(x, y);
			}
			if(getGridSquareComponentRelative(x, y, Direction.DOWN) != null) {
				LogicSimulator.instance.prepareDrawTexture(component.powered ? Texture.WIRE_DOWN_POWERED : Texture.WIRE_DOWN);
				LogicSimulator.instance.drawTextureGridPosition(x, y);
			}
			if(getGridSquareComponentRelative(x, y, Direction.LEFT) != null) {
				LogicSimulator.instance.prepareDrawTexture(component.powered ? Texture.WIRE_LEFT_POWERED : Texture.WIRE_LEFT);
				LogicSimulator.instance.drawTextureGridPosition(x, y);
			}
			if(getGridSquareComponentRelative(x, y, Direction.RIGHT) != null) {
				LogicSimulator.instance.prepareDrawTexture(component.powered ? Texture.WIRE_RIGHT_POWERED : Texture.WIRE_RIGHT);
				LogicSimulator.instance.drawTextureGridPosition(x, y);
			}

			LogicSimulator.instance.prepareDrawTexture(component.powered ? Texture.WIRE_CENTER_POWERED : Texture.WIRE_CENTER);
			LogicSimulator.instance.drawTextureGridPosition(x, y);

			return;
		}

		if(component instanceof InverterComponent) {
			Texture topTexture = null;
			switch(((InverterComponent) component).getDirection()) {
				case Direction.UP: {
					topTexture = component.powered ? Texture.INVERTER_UP_POWERED : Texture.INVERTER_UP;
					break;
				}
				case Direction.DOWN: {
					topTexture = component.powered ? Texture.INVERTER_DOWN_POWERED : Texture.INVERTER_DOWN;
					break;
				}
				case Direction.LEFT: {
					topTexture = component.powered ? Texture.INVERTER_LEFT_POWERED : Texture.INVERTER_LEFT;
					break;
				}
				case Direction.RIGHT: {
					topTexture = component.powered ? Texture.INVERTER_RIGHT_POWERED : Texture.INVERTER_RIGHT;
					break;
				}
			}
			if(getGridSquareComponentRelative(x, y, Direction.UP) instanceof WireComponent) {
				LogicSimulator.instance.prepareDrawTexture(component.powered ? Texture.WIRE_UP_POWERED : Texture.WIRE_UP);
				LogicSimulator.instance.drawTextureGridPosition(x, y);
			}
			if(getGridSquareComponentRelative(x, y, Direction.DOWN) instanceof WireComponent) {
				LogicSimulator.instance.prepareDrawTexture(component.powered ? Texture.WIRE_DOWN_POWERED : Texture.WIRE_DOWN);
				LogicSimulator.instance.drawTextureGridPosition(x, y);
			}
			if(getGridSquareComponentRelative(x, y, Direction.LEFT) instanceof WireComponent) {
				LogicSimulator.instance.prepareDrawTexture(component.powered ? Texture.WIRE_LEFT_POWERED : Texture.WIRE_LEFT);
				LogicSimulator.instance.drawTextureGridPosition(x, y);
			}
			if(getGridSquareComponentRelative(x, y, Direction.RIGHT) instanceof WireComponent) {
				LogicSimulator.instance.prepareDrawTexture(component.powered ? Texture.WIRE_RIGHT_POWERED : Texture.WIRE_RIGHT);
				LogicSimulator.instance.drawTextureGridPosition(x, y);
			}

			if(topTexture != null) {
				LogicSimulator.instance.prepareDrawTexture(topTexture);
				LogicSimulator.instance.drawTextureGridPosition(x, y);

				return;
			}
		}

		// TODO - Future
	}
	
	public GridSquare getGridSquareRelative(int x, int y, int direction) {
		switch(direction) {
			case Direction.UP: {
				y--;
				break;
			}
			case Direction.DOWN: {
				y++;
				break;
			}
			case Direction.LEFT: {
				x--;
				break;
			}
			case Direction.RIGHT: {
				x++;
				break;
			}
		}

		if(x < 0 || x >= LogicSimulator.GRID_WIDTH || y < 0 || y >= LogicSimulator.GRID_HEIGHT) {
			return null;
		}

		return LogicSimulator.grid[x][y];
	}

	public GridSquare getGridSquare(int x, int y) {
		if(x < 0 || x >= LogicSimulator.GRID_WIDTH || y < 0 || y >= LogicSimulator.GRID_HEIGHT) {
			return null;
		}

		return LogicSimulator.grid[x][y];
	}

	public GridComponent getGridSquareComponentRelative(int x, int y, int direction) {
		switch(direction) {
			case Direction.UP: {
				y--;
				break;
			}
			case Direction.DOWN: {
				y++;
				break;
			}
			case Direction.LEFT: {
				x--;
				break;
			}
			case Direction.RIGHT: {
				x++;
				break;
			}
		}

		if(x < 0 || x >= LogicSimulator.GRID_WIDTH || y < 0 || y >= LogicSimulator.GRID_HEIGHT) {
			return null;
		}

		return LogicSimulator.grid[x][y].component;
	}

	public GridComponent getGridSquareComponent(int x, int y) {
		if(x < 0 || x >= LogicSimulator.GRID_WIDTH || y < 0 || y >= LogicSimulator.GRID_HEIGHT) {
			return null;
		}

		return LogicSimulator.grid[x][y].component;
	}

	public void powerGridSquare(int x, int y) {
		if(x < 0 || x >= LogicSimulator.GRID_WIDTH || y < 0 || y >= LogicSimulator.GRID_HEIGHT) {
			return;
		}

		if(!LogicSimulator.grid[x][y].component.powered) {
			LogicSimulator.grid[x][y].component.powered = true;
		}
	}

	public void powerGridSquareAndUpdate(int x, int y) {
		if(x < 0 || x >= LogicSimulator.GRID_WIDTH || y < 0 || y >= LogicSimulator.GRID_HEIGHT) {
			return;
		}

		if(!LogicSimulator.grid[x][y].component.powered) {
			LogicSimulator.grid[x][y].component.powered = true;
			LogicSimulator.grid[x][y].update();
		}
	}
}
