package com.github.xt449.logicsimulator;

import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
abstract class SpriteRenderer {

	private static final float[] vertices = {
			0, 1,
			1, 0,
			0, 0,

			0, 1,
			1, 1,
			1, 0,
	};

	static void initialize() {
		// VAO
		int vao = glGenVertexArrays();

		// VBO
		int vbo = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		// VAO
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	static void draw() {

	}
}
