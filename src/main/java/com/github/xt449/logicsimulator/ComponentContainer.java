package com.github.xt449.logicsimulator;

import java.util.Objects;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class ComponentContainer {

	final int x;
	final int y;

	Component component = null;

	public ComponentContainer(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/*public void preTick() {
		if(component != null) {
			component.tick(this);
		}
	}*/

	public void tick() {
		if(component != null) {
			component.tick(this);
		}
	}

	public void updateState() {
		if(component instanceof DelayedComponent) {
			((DelayedComponent) component).updateState(this);
		}
	}

	public void render() {
		if(component != null) {
			component.render(this);
		}
	}

	public ComponentContainer getRelativeGridSquare(int direction) {
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

		return LogicSimulator.instance.getComponentContainerAt(x, y);
	}

	public Component getRelativeGridComponent(int direction) {
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

		return LogicSimulator.instance.getComponentAt(x, y);
	}

	@Override
	public boolean equals(Object other) {
		if(this == other) return true;
		if(other == null || getClass() != other.getClass()) return false;
		ComponentContainer square = (ComponentContainer) other;
		return x == square.x && y == square.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
