package com.github.xt449.logicsimulator;

import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public abstract class GLHelper {

	static void drawRectangle(Object texture, Object coordinates) {
		glDrawBuffers(coordinates.hashCode());
	}
}
