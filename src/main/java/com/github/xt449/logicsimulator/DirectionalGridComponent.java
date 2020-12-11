package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public abstract class DirectionalGridComponent extends GridComponent implements Directional {

	protected int direction;

	public DirectionalGridComponent() {
		this.direction = Direction.UP;
	}

	public DirectionalGridComponent(int direction) {
		this.direction = direction;
	}

	@Override
	public int getDirection() {
		return direction;
	}

	@Override
	public void rotate() {
		switch(direction) {
			case Direction.UP: {
				direction = Direction.RIGHT;
				break;
			}
			case Direction.DOWN: {
				direction = Direction.LEFT;
				break;
			}
			case Direction.LEFT: {
				direction = Direction.UP;
				break;
			}
			case Direction.RIGHT: {
				direction = Direction.DOWN;
				break;
			}
		}
	}
}
