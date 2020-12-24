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

	public final int width;
	public final int height;

	public final int id;

	public Texture(String fileName) {
		final IntBuffer widthPointer = BufferUtils.createIntBuffer(1);
		final IntBuffer heightPointer = BufferUtils.createIntBuffer(1);
		final IntBuffer channels = BufferUtils.createIntBuffer(1);

		final ByteBuffer byteBuffer = STBImage.stbi_load(LogicSimulator.class.getResource("/textures/" + fileName).getPath().substring(1), widthPointer, heightPointer, channels, STBImage.STBI_rgb_alpha).asReadOnlyBuffer();

		width = widthPointer.get(0);
		height = heightPointer.get(0);

		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
