package com.github.xt449.logicsimulator;

import java.util.Objects;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class ComponentContainer {

	final LogicSimulator simulator;

	final int x;
	final int y;

	Component component = null;

	public ComponentContainer(LogicSimulator simulator, int x, int y) {
		this.simulator = simulator;

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

		return simulator.grid.getComponentContainerAt(x, y);
	}

	public ComponentContainer getRelativeGridComponent(int direction) {
		switch(direction) {
			case Direction.UP:
				return simulator.grid.getComponentContainerAt(x, y - 1);
			case Direction.DOWN:
				return simulator.grid.getComponentContainerAt(x, y + 1);
			case Direction.LEFT:
				return simulator.grid.getComponentContainerAt(x - 1, y);
			case Direction.RIGHT:
				return simulator.grid.getComponentContainerAt(x + 1, y);
		}

		return null;
	}

	public Component getRelativeComponent(int direction) {
		switch(direction) {
			case Direction.UP:
				return simulator.grid.getComponentAt(x, y - 1);
			case Direction.DOWN:
				return simulator.grid.getComponentAt(x, y + 1);
			case Direction.LEFT:
				return simulator.grid.getComponentAt(x - 1, y);
			case Direction.RIGHT:
				return simulator.grid.getComponentAt(x + 1, y);
		}

		return null;
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
