package com.github.xt449.logicsimulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.stream.Collectors;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
abstract class ResourceLoader {

	static String readAllLines(String resourceLocation) {
		return new BufferedReader(new InputStreamReader(LogicSimulator.class.getResourceAsStream(resourceLocation))).lines().collect(Collectors.joining("\n"));
	}

	static ByteBuffer readBytes(String resourceLocation) {
		try(final InputStream byteStream = LogicSimulator.class.getResourceAsStream(resourceLocation)) {
			final ByteBuffer buffer = ByteBuffer.allocateDirect(byteStream.available());
			final byte[] data = new byte[byteStream.available()];
			byteStream.read(data);
			buffer.put(data);
			buffer.flip();
			return buffer;
		} catch(IOException exc) {
			exc.printStackTrace();
		}

		return ByteBuffer.allocateDirect(0);
	}
}
