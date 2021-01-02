package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public abstract class Textures {

	static void init() {
		System.out.println("First texture ID is " + BOX_9SLICE.id);
	}

	// Box

	static final Texture BOX_9SLICE = new Texture("box_9slice.png");

	// Nodes

	static final Texture NODE_INPUT = new Texture("node_input.png");
	static final Texture NODE_OUTPUT = new Texture("node_output.png");

	// Inverters

	static final Texture INVERTER_RIGHT = new Texture("inverter_right.png");
	static final Texture INVERTER_RIGHT_POWERED = new Texture("inverter_right_powered.png");

	// Switches

	static final Texture SWITCH_OFF = new Texture("switch_off.png");
	static final Texture SWITCH_ON = new Texture("switch_on.png");

	// Trash Can

	static final Texture TRASH_CAN = new Texture("trash_can.png");
}
