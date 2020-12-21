package com.github.xt449.logicsimulator;

import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class ShaderProgram {

	final int program;

	ShaderProgram(CharSequence vertexShaderSource, CharSequence fragmentShaderSource) {
		program = glCreateProgram();

		final int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vertexShaderSource);
		glCompileShader(vertexShader);
		if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(vertexShader));
		}
		glAttachShader(program, vertexShader);

		final int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fragmentShaderSource);
		glCompileShader(fragmentShader);
		if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(fragmentShader));
		}
		glAttachShader(program, fragmentShader);

		glLinkProgram(program);
		if(glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
			System.out.println(glGetProgramInfoLog(program));
		}

		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
	}
}
