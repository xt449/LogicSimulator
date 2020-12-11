package com.github.xt449.logicsimulator;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class Texture {

	final int width;
	final int height;

	final int id;

	private Texture(String fileName) {
		final IntBuffer widthPointer = BufferUtils.createIntBuffer(1);
		final IntBuffer heightPointer = BufferUtils.createIntBuffer(1);
		final IntBuffer channels = BufferUtils.createIntBuffer(1);

		final ByteBuffer byteBuffer = STBImage.stbi_load(LogicSimulator.class.getResource("/textures/" + fileName).getPath().substring(1), widthPointer, heightPointer, channels, STBImage.STBI_rgb_alpha).asReadOnlyBuffer();

		width = widthPointer.get(0);
		height = heightPointer.get(0);

		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	// Static

	static void init() {
		System.out.println("First texture ID is " + CELL.id);
	}

	static final Texture CELL = new Texture("cell.png");

	static final Texture WIRE_CENTER = new Texture("wire_center.png");
	static final Texture WIRE_CENTER_POWERED = new Texture("wire_center_powered.png");

	// Wires

	static final Texture WIRE_UP = new Texture("wire_up.png");
	static final Texture WIRE_DOWN = new Texture("wire_down.png");
	static final Texture WIRE_LEFT = new Texture("wire_left.png");
	static final Texture WIRE_RIGHT = new Texture("wire_right.png");

	static final Texture[] WIRES = {
			WIRE_UP,
			WIRE_DOWN,
			WIRE_LEFT,
			WIRE_RIGHT,
	};

	// Wires Powered

	static final Texture WIRE_UP_POWERED = new Texture("wire_up_powered.png");
	static final Texture WIRE_DOWN_POWERED = new Texture("wire_down_powered.png");
	static final Texture WIRE_LEFT_POWERED = new Texture("wire_left_powered.png");
	static final Texture WIRE_RIGHT_POWERED = new Texture("wire_right_powered.png");

	static final Texture[] WIRES_POWERED = {
			WIRE_UP_POWERED,
			WIRE_DOWN_POWERED,
			WIRE_LEFT_POWERED,
			WIRE_RIGHT_POWERED,
	};

	// Inverters

	static final Texture INVERTER_UP = new Texture("inverter_up.png");
	static final Texture INVERTER_DOWN = new Texture("inverter_down.png");
	static final Texture INVERTER_LEFT = new Texture("inverter_left.png");
	static final Texture INVERTER_RIGHT = new Texture("inverter_right.png");

	static final Texture[] INVERTERS = {
			INVERTER_UP,
			INVERTER_DOWN,
			INVERTER_LEFT,
			INVERTER_RIGHT,
	};

	// Inverters Powered

	static final Texture INVERTER_UP_POWERED = new Texture("inverter_up_powered.png");
	static final Texture INVERTER_DOWN_POWERED = new Texture("inverter_down_powered.png");
	static final Texture INVERTER_LEFT_POWERED = new Texture("inverter_left_powered.png");
	static final Texture INVERTER_RIGHT_POWERED = new Texture("inverter_right_powered.png");

	static final Texture[] INVERTERS_POWERED = {
			INVERTER_UP_POWERED,
			INVERTER_DOWN_POWERED,
			INVERTER_LEFT_POWERED,
			INVERTER_RIGHT_POWERED,
	};

	// Diodes

	static final Texture DIODE_UP = new Texture("diode_up.png");
	static final Texture DIODE_DOWN = new Texture("diode_down.png");
	static final Texture DIODE_LEFT = new Texture("diode_left.png");
	static final Texture DIODE_RIGHT = new Texture("diode_right.png");

	static final Texture[] DIODES = {
			DIODE_UP,
			DIODE_DOWN,
			DIODE_LEFT,
			DIODE_RIGHT,
	};

	// Diodes Powered

	static final Texture DIODE_UP_POWERED = new Texture("diode_up_powered.png");
	static final Texture DIODE_DOWN_POWERED = new Texture("diode_down_powered.png");
	static final Texture DIODE_LEFT_POWERED = new Texture("diode_left_powered.png");
	static final Texture DIODE_RIGHT_POWERED = new Texture("diode_right_powered.png");

	static final Texture[] DIODES_POWERED = {
			DIODE_UP_POWERED,
			DIODE_DOWN_POWERED,
			DIODE_LEFT_POWERED,
			DIODE_RIGHT_POWERED,
	};

	// Arrows

	static final Texture ARROW_UP = new Texture("arrow_up.png");
	static final Texture ARROW_DOWN = new Texture("arrow_down.png");
	static final Texture ARROW_LEFT = new Texture("arrow_left.png");
	static final Texture ARROW_RIGHT = new Texture("arrow_right.png");

	static final Texture[] ARROWS = {
			ARROW_UP,
			ARROW_DOWN,
			ARROW_LEFT,
			ARROW_RIGHT,
	};

	static Texture getWire(int direction) {
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

	static Texture getPoweredWire(int direction) {
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

	static Texture getInverter(int direction) {
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

	static Texture getPoweredInverter(int direction) {
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

	static Texture getDiode(int direction) {
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

	static Texture getPoweredDiode(int direction) {
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

	static Texture getArrow(int direction) {
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
}
