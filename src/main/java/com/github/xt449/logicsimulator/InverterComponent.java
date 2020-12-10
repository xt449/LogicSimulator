package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class InverterComponent extends GridComponent implements Directional {

	private int direction;

	public InverterComponent() {
		this.direction = Direction.UP;
	}

	public InverterComponent(int direction) {
		this.direction = direction;
	}

	@Override
	boolean redirectsWireFrom(int direction) {
		return this.direction == direction || Direction.getDirectionReversed(this.direction) == direction;
	}

	@Override
	public int getDirection() {
		return direction;
	}

	@Override
	public void rotate() {
		if(++direction % 4 == 0) {
			direction -= 4;
		}
	}
}
