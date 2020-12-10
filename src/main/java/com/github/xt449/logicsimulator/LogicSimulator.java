package com.github.xt449.logicsimulator;

import org.joml.Matrix4f;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public final class LogicSimulator extends GLFWManager {

	public static void main(String[] args) {
		new LogicSimulator().run();
	}

	int orthograhpicProgram;
	int vbo;
	int vao;

	private Texture currentTexture;

	public void run() {
		System.out.println("LWJGL " + Version.getVersion());

		// Initialize
		initialize();

		GL.createCapabilities();
		glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Program and Shaders
		orthograhpicProgram = glCreateProgram();

		final int orthograhpicVertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(orthograhpicVertexShader, ResourceLoader.readAllLines("/simple.vsh"));
		glCompileShader(orthograhpicVertexShader);
		if(glGetShaderi(orthograhpicVertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(orthograhpicVertexShader));
		}
		glAttachShader(orthograhpicProgram, orthograhpicVertexShader);

		final int orthograhpicFragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(orthograhpicFragmentShader, ResourceLoader.readAllLines("/simple.fsh"));
		glCompileShader(orthograhpicFragmentShader);
		if(glGetShaderi(orthograhpicFragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(orthograhpicFragmentShader));
		}
		glAttachShader(orthograhpicProgram, orthograhpicFragmentShader);

		glLinkProgram(orthograhpicProgram);
		if(glGetProgrami(orthograhpicProgram, GL_LINK_STATUS) == GL_FALSE) {
			System.out.println(glGetProgramInfoLog(orthograhpicProgram));
		}
		//glUseProgram(orthograhpicProgram);

		glDeleteShader(orthograhpicVertexShader);
		glDeleteShader(orthograhpicFragmentShader);

		// VBO
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		// VAO
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

		// Textures
		Texture.init();

		// Loop
		loop();

		// Exit
		exit();
	}

	private final float[] vertices = {
			// pos      // tex
			0.0F, 1.0F, 0.0F, 1.0F,
			1.0F, 0.0F, 1.0F, 0.0F,
			0.0F, 0.0F, 0.0F, 0.0F,

			0.0F, 1.0F, 0.0F, 1.0F,
			1.0F, 1.0F, 1.0F, 1.0F,
			1.0F, 0.0F, 1.0F, 0.0F,
	};

	private void loop() {
		do {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			render();

			swapBuffers();
			pollEvents();
		} while(!shouldClose());
	}

	private void render() {
		prepareDrawTexture(Texture.CELL);
		for(int y = 0; y < 27; y++) {
			for(int x = 0; x < 48; x++) {
				drawTexture(x, y);
			}
		}

		//

		prepareDrawTexture(Texture.WIRE_CENTER);
		drawTexture(3, 0);

		prepareDrawTexture(Texture.WIRE_UP);

		prepareDrawTexture(Texture.WIRE_DOWN);

		prepareDrawTexture(Texture.WIRE_LEFT);
		drawTexture(0, 0);
		drawTexture(0, 1);
		drawTexture(0, 2);
		drawTexture(0, 3);
		drawTexture(3, 0);

		prepareDrawTexture(Texture.WIRE_RIGHT);
		drawTexture(2, 0);

		//

		prepareDrawTexture(Texture.WIRE_CENTER_POWERED);
		drawTexture(1, 0);
		drawTexture(1, 1);
		drawTexture(1, 2);
		drawTexture(1, 3);

		prepareDrawTexture(Texture.WIRE_UP_POWERED);
		drawTexture(1, 1);
		drawTexture(1, 2);
		drawTexture(1, 3);

		prepareDrawTexture(Texture.WIRE_DOWN_POWERED);
		drawTexture(1, 0);
		drawTexture(1, 1);
		drawTexture(1, 2);

		prepareDrawTexture(Texture.WIRE_LEFT_POWERED);
		drawTexture(1, 0);
		drawTexture(1, 1);
		drawTexture(1, 2);
		drawTexture(1, 3);
		drawTexture(2, 0);

		prepareDrawTexture(Texture.WIRE_RIGHT_POWERED);
		drawTexture(0, 0);
		drawTexture(0, 1);
		drawTexture(0, 2);
		drawTexture(0, 3);
		drawTexture(1, 0);

		//

		prepareDrawTexture(Texture.INVERTER_UP);

		prepareDrawTexture(Texture.INVERTER_DOWN);

		prepareDrawTexture(Texture.INVERTER_LEFT);

		prepareDrawTexture(Texture.INVERTER_RIGHT);
		drawTexture(0, 0);
		drawTexture(0, 1);
		drawTexture(0, 2);
		drawTexture(0, 3);

		//

		prepareDrawTexture(Texture.INVERTER_UP_POWERED);

		prepareDrawTexture(Texture.INVERTER_DOWN_POWERED);

		prepareDrawTexture(Texture.INVERTER_LEFT_POWERED);

		prepareDrawTexture(Texture.INVERTER_RIGHT_POWERED);
		drawTexture(2, 0);

		//
	}

	private void prepareDrawTexture(Texture texture) {
		currentTexture = texture;

		glUseProgram(orthograhpicProgram);

		//

		glUniform1i(glGetUniformLocation(orthograhpicProgram, "image"), 0);

		//

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, currentTexture.id);

		glBindVertexArray(vao);
	}

	private void drawTexture() {
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram, "model"), false,
				new Matrix4f()
						.scale(currentTexture.width, currentTexture.height, 0)
						.get(new float[16])
		);
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram, "projection"), false,
				new Matrix4f().ortho(0, windowSize.x, windowSize.y, 0, -1, 1)
						.get(new float[16])
		);

		glDrawArrays(GL_TRIANGLES, 0, 6);

		//glBindTexture(GL_TEXTURE_2D, 0);
		//glBindVertexArray(0);
	}

	private void drawTexture(int xOffset, int yOffset) {
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram, "model"), false,
				new Matrix4f().translate(xOffset * currentTexture.width, yOffset * currentTexture.height, 0)
						.scale(currentTexture.width, currentTexture.height, 0)
						.get(new float[16])
		);
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram, "projection"), false,
				new Matrix4f().ortho(0, windowSize.x, windowSize.y, 0, -1, 1)
						.get(new float[16])
		);

		glDrawArrays(GL_TRIANGLES, 0, 6);

		//glBindTexture(GL_TEXTURE_2D, 0);
		//glBindVertexArray(0);
	}

	private void drawTextureExactPosition(float xRatio, float yRatio) {
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram, "model"), false,
				new Matrix4f().translate(xRatio * windowSize.x, yRatio * windowSize.y, 0)
						.scale(currentTexture.width, currentTexture.height, 0)
						.get(new float[16])
		);
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram, "projection"), false,
				new Matrix4f().ortho(0, windowSize.x, windowSize.y, 0, -1, 1)
						.get(new float[16])
		);

		glDrawArrays(GL_TRIANGLES, 0, 6);

		//glBindTexture(GL_TEXTURE_2D, 0);
		//glBindVertexArray(0);
	}

	private void drawTextureRatioPosition(int xOffset, int yOffset) {
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram, "model"), false,
				new Matrix4f().translate(xOffset * currentTexture.width, yOffset * currentTexture.height, 0)
						.scale(currentTexture.width, currentTexture.height, 0)
						.get(new float[16])
		);
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram, "projection"), false,
				new Matrix4f().ortho(0, windowSize.x, windowSize.y, 0, -1, 1)
						.get(new float[16])
		);

		glDrawArrays(GL_TRIANGLES, 0, 6);

		//glBindTexture(GL_TEXTURE_2D, 0);
		//glBindVertexArray(0);
	}

	@Override
	void keyCallback(long window, int key, int scancode, int action, int mods) {

	}

	@Override
	void mouseButtonCallback(long window, int button, int action, int mods) {

	}
}
