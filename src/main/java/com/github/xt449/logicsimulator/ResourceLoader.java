package com.github.xt449.logicsimulator;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.stream.Collectors;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
abstract class ResourceLoader {

	static String readAllLines(String resourceLocation) {
		return new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(resourceLocation))).lines().collect(Collectors.joining("\n"));
	}

	static IntBuffer widthPointer;
	static IntBuffer heightPointer;
	static IntBuffer channels;

	static ByteBuffer readBytesImage(String resourceLocation) {
		widthPointer = BufferUtils.createIntBuffer(1);
		heightPointer = BufferUtils.createIntBuffer(1);
		channels = BufferUtils.createIntBuffer(1);
		return STBImage.stbi_load(Main.class.getResource(resourceLocation).getPath().substring(1), widthPointer, heightPointer, channels, STBImage.STBI_rgb_alpha);
	}
}
