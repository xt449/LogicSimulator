package com.github.xt449.logicsimulator;

import org.joml.Matrix4f;
import org.joml.Vector3f;
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

		final ByteBuffer byteBuffer = STBImage.stbi_load(Main.class.getResource("/textures/" + fileName).getPath().substring(1), widthPointer, heightPointer, channels, STBImage.STBI_rgb_alpha).asReadOnlyBuffer();

		width = widthPointer.get(0);
		height = heightPointer.get(0);

		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);
		//glBindTexture(GL_TEXTURE_2D, 0);

		//setup();

		/*glActiveTexture(GL_TEXTURE0 + id);
		glBindTexture(GL_TEXTURE_2D, id);
		glBindTexture(GL_TEXTURE_2D, 0);

		try {
			TimeUnit.MILLISECONDS.sleep(1);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}*/

		/*glActiveTexture(0);*/
	}

	void render(Main main) {
		glUseProgram(main.orthograhpicProgram);

		glUniformMatrix4fv(glGetUniformLocation(main.orthograhpicProgram, "model"), false,
				new Matrix4f().translate(new Vector3f(0.5F, (this == Texture.CELL ? -0.02F : 0) + 0.5F, 0).mul(new Vector3f(main.windowSize.x, main.windowSize.y, 0)))
						.scale(32)
						.get(new float[16])
		);
		glUniformMatrix4fv(glGetUniformLocation(main.orthograhpicProgram, "projection"), false,
				new Matrix4f().ortho(0, main.windowSize.x, main.windowSize.y, 0, -1, 1)
						.get(new float[16])
		);

		glUniform1i(glGetUniformLocation(main.orthograhpicProgram, "image"), 0);

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, id);

		glBindVertexArray(main.vao);
		glDrawArrays(GL_TRIANGLES, 0, 6);

		glBindTexture(GL_TEXTURE_2D, 0);
		glBindVertexArray(0);
	}

	/*private void setup() {
		//if(glID == 0) {
			glID = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, glID);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);
			glBindTexture(GL_TEXTURE_2D, 0);
		//}
	}*/

	/*public int getID() {
		return glID;
	}*/

	// Static

	static void init() {
		System.out.println("First texture ID is " + CELL.id);

//		glActiveTexture(GL_TEXTURE0 + Texture.CELL.id);
//		glBindTexture(GL_TEXTURE_2D, Texture.CELL.id);
//
//		glActiveTexture(GL_TEXTURE0 + Texture.INVERTER_UP.id);
//		glBindTexture(GL_TEXTURE_2D, Texture.INVERTER_UP.id);
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
