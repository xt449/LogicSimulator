package com.github.xt449.logicsimulator;

import javafx.scene.image.Image;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public abstract class Textures {

	static final Image CELL = new Image(LogicSimulator.class.getResourceAsStream("/textures/cell.png"));

	static final Image WIRE_CENTER = new Image(LogicSimulator.class.getResourceAsStream("/textures/wire_center.png"));
	static final Image WIRE_CENTER_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/wire_center_powered.png"));

	// Wires

	static final Image WIRE_UP = new Image(LogicSimulator.class.getResourceAsStream("/textures/wire_up.png"));
	static final Image WIRE_DOWN = new Image(LogicSimulator.class.getResourceAsStream("/textures/wire_down.png"));
	static final Image WIRE_LEFT = new Image(LogicSimulator.class.getResourceAsStream("/textures/wire_left.png"));
	static final Image WIRE_RIGHT = new Image(LogicSimulator.class.getResourceAsStream("/textures/wire_right.png"));

	static final Image[] WIRES = {
			WIRE_UP,
			WIRE_DOWN,
			WIRE_LEFT,
			WIRE_RIGHT,
	};

	// Wires Powered

	static final Image WIRE_UP_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/wire_up_powered.png"));
	static final Image WIRE_DOWN_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/wire_down_powered.png"));
	static final Image WIRE_LEFT_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/wire_left_powered.png"));
	static final Image WIRE_RIGHT_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/wire_right_powered.png"));

	static final Image[] WIRES_POWERED = {
			WIRE_UP_POWERED,
			WIRE_DOWN_POWERED,
			WIRE_LEFT_POWERED,
			WIRE_RIGHT_POWERED,
	};

	// Inverters

	static final Image INVERTER_UP = new Image(LogicSimulator.class.getResourceAsStream("/textures/inverter_up.png"));
	static final Image INVERTER_DOWN = new Image(LogicSimulator.class.getResourceAsStream("/textures/inverter_down.png"));
	static final Image INVERTER_LEFT = new Image(LogicSimulator.class.getResourceAsStream("/textures/inverter_left.png"));
	static final Image INVERTER_RIGHT = new Image(LogicSimulator.class.getResourceAsStream("/textures/inverter_right.png"));

	static final Image[] INVERTERS = {
			INVERTER_UP,
			INVERTER_DOWN,
			INVERTER_LEFT,
			INVERTER_RIGHT,
	};

	// Inverters Powered

	static final Image INVERTER_UP_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/inverter_up_powered.png"));
	static final Image INVERTER_DOWN_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/inverter_down_powered.png"));
	static final Image INVERTER_LEFT_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/inverter_left_powered.png"));
	static final Image INVERTER_RIGHT_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/inverter_right_powered.png"));

	static final Image[] INVERTERS_POWERED = {
			INVERTER_UP_POWERED,
			INVERTER_DOWN_POWERED,
			INVERTER_LEFT_POWERED,
			INVERTER_RIGHT_POWERED,
	};

	// Diodes

	static final Image DIODE_UP = new Image(LogicSimulator.class.getResourceAsStream("/textures/diode_up.png"));
	static final Image DIODE_DOWN = new Image(LogicSimulator.class.getResourceAsStream("/textures/diode_down.png"));
	static final Image DIODE_LEFT = new Image(LogicSimulator.class.getResourceAsStream("/textures/diode_left.png"));
	static final Image DIODE_RIGHT = new Image(LogicSimulator.class.getResourceAsStream("/textures/diode_right.png"));

	static final Image[] DIODES = {
			DIODE_UP,
			DIODE_DOWN,
			DIODE_LEFT,
			DIODE_RIGHT,
	};

	// Diodes Powered
	static final Image DIODE_UP_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/diode_up_powered.png"));
	static final Image DIODE_DOWN_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/diode_down_powered.png"));
	static final Image DIODE_LEFT_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/diode_left_powered.png"));
	static final Image DIODE_RIGHT_POWERED = new Image(LogicSimulator.class.getResourceAsStream("/textures/diode_right_powered.png"));

	static final Image[] DIODES_POWERED = {
			DIODE_UP_POWERED,
			DIODE_DOWN_POWERED,
			DIODE_LEFT_POWERED,
			DIODE_RIGHT_POWERED,
	};

	// Arrows

	static final Image ARROW_UP = new Image(LogicSimulator.class.getResourceAsStream("/textures/arrow_up.png"));
	static final Image ARROW_DOWN = new Image(LogicSimulator.class.getResourceAsStream("/textures/arrow_down.png"));
	static final Image ARROW_LEFT = new Image(LogicSimulator.class.getResourceAsStream("/textures/arrow_left.png"));
	static final Image ARROW_RIGHT = new Image(LogicSimulator.class.getResourceAsStream("/textures/arrow_right.png"));

	static final Image[] ARROWS = {
			ARROW_UP,
			ARROW_DOWN,
			ARROW_LEFT,
			ARROW_RIGHT,
	};

	// Switches

	static final Image SWITCH_OFF = new Image(LogicSimulator.class.getResourceAsStream("/textures/switch_off.png"));
	static final Image SWITCH_ON = new Image(LogicSimulator.class.getResourceAsStream("/textures/switch_on.png"));

	// Bridges

	static final Image BRIDGE_NONE = new Image(LogicSimulator.class.getResourceAsStream("/textures/bridge_none.png"));
	static final Image BRIDGE_HORIZONTAL = new Image(LogicSimulator.class.getResourceAsStream("/textures/bridge_horizontal.png"));
	static final Image BRIDGE_VERTICAL = new Image(LogicSimulator.class.getResourceAsStream("/textures/bridge_vertical.png"));
	static final Image BRIDGE_BOTH = new Image(LogicSimulator.class.getResourceAsStream("/textures/bridge_both.png"));

	// Getters

	static Image getWire(int direction) {
		switch(direction) {
			case Direction.UP:
				return WIRE_UP;
			case Direction.DOWN:
				return WIRE_DOWN;
			case Direction.LEFT:
				return WIRE_LEFT;
			case Direction.RIGHT:
				return WIRE_RIGHT;
			default:
				return null;
		}
	}

	static Image getPoweredWire(int direction) {
		switch(direction) {
			case Direction.UP:
				return WIRE_UP_POWERED;
			case Direction.DOWN:
				return WIRE_DOWN_POWERED;
			case Direction.LEFT:
				return WIRE_LEFT_POWERED;
			case Direction.RIGHT:
				return WIRE_RIGHT_POWERED;
			default:
				return null;
		}
	}

	static Image getInverter(int direction) {
		switch(direction) {
			case Direction.UP:
				return INVERTER_UP;
			case Direction.DOWN:
				return INVERTER_DOWN;
			case Direction.LEFT:
				return INVERTER_LEFT;
			case Direction.RIGHT:
				return INVERTER_RIGHT;
			default:
				return null;
		}
	}

	static Image getPoweredInverter(int direction) {
		switch(direction) {
			case Direction.UP:
				return INVERTER_UP_POWERED;
			case Direction.DOWN:
				return INVERTER_DOWN_POWERED;
			case Direction.LEFT:
				return INVERTER_LEFT_POWERED;
			case Direction.RIGHT:
				return INVERTER_RIGHT_POWERED;
			default:
				return null;
		}
	}

	static Image getDiode(int direction) {
		switch(direction) {
			case Direction.UP:
				return DIODE_UP;
			case Direction.DOWN:
				return DIODE_DOWN;
			case Direction.LEFT:
				return DIODE_LEFT;
			case Direction.RIGHT:
				return DIODE_RIGHT;
			default:
				return null;
		}
	}

	static Image getPoweredDiode(int direction) {
		switch(direction) {
			case Direction.UP:
				return DIODE_UP_POWERED;
			case Direction.DOWN:
				return DIODE_DOWN_POWERED;
			case Direction.LEFT:
				return DIODE_LEFT_POWERED;
			case Direction.RIGHT:
				return DIODE_RIGHT_POWERED;
			default:
				return null;
		}
	}

	static Image getArrow(int direction) {
		switch(direction) {
			case Direction.UP:
				return ARROW_UP;
			case Direction.DOWN:
				return ARROW_DOWN;
			case Direction.LEFT:
				return ARROW_LEFT;
			case Direction.RIGHT:
				return ARROW_RIGHT;
			default:
				return null;
		}
	}

	static Image getBridge(boolean horizontal, boolean vertical) {
		if(horizontal) {
			if(vertical) {
				return BRIDGE_BOTH;
			} else {
				return BRIDGE_HORIZONTAL;
			}
		} else {
			if(vertical) {
				return BRIDGE_VERTICAL;
			} else {
				return BRIDGE_NONE;
			}
		}
	}
}
