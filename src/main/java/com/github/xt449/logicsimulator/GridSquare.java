package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class GridSquare {

	final int x;
	final int y;

	GridComponent component = null;

	public GridSquare(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void tick() {
		if(component != null) {
			component.tick(this);
			component.update(this);
		}
	}

	public void render() {
		if(component != null) {
			component.render(this);
		}
	}

	public GridSquare getRelativeGridSquare(int direction) {
		int x = this.x;
		int y = this.y;

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

		return LogicSimulator.getGridSquare(x, y);
	}

	public GridComponent getRelativeGridComponent(int direction) {
		int x = this.x;
		int y = this.y;

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

		return LogicSimulator.getGridComponent(x, y);
	}
}
