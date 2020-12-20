package com.github.xt449.logicsimulator;

import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class VertexObject {

	final int vbo;
	final int vao;

	VertexObject(float[] verticesData, int vertexSize) {
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, verticesData, GL_STATIC_DRAW);

		// VAO
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, vertexSize, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
}
