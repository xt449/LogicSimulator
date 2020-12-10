package com.github.xt449.logicsimulator;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
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
}
