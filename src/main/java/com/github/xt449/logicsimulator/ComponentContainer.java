package com.github.xt449.logicsimulator;

import javafx.scene.Group;

import java.util.Objects;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class ComponentContainer extends Group {

	final ComponentGridPane gridPane;
	final int x;
	final int y;

	Component component = null;

	public ComponentContainer(ComponentGridPane gridPane, int x, int y) {
		this.gridPane = gridPane;
		this.x = x;
		this.y = y;
	}

	public void tick() {
		if(component != null) {
			component.tick(this);
		}
	}

	public void updateState() {
		if(component instanceof DelayedComponent) {
			((DelayedComponent) component).updateState(this);
		}

		getChildren().clear();
		if(component != null) {
			getChildren().addAll(component.getImages(this));
		}
	}

	public ComponentContainer getRelativeComponentContainer(int direction) {
		switch(direction) {
			case Direction.UP:
				return gridPane.getComponentContainerAt(x, y - 1);
			case Direction.DOWN:
				return gridPane.getComponentContainerAt(x, y + 1);
			case Direction.LEFT:
				return gridPane.getComponentContainerAt(x - 1, y);
			case Direction.RIGHT:
				return gridPane.getComponentContainerAt(x + 1, y);
		}

		return null;
	}

	public Component getRelativeComponent(int direction) {
		return getRelativeComponentContainer(direction).component;
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
