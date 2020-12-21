package com.github.xt449.logicsimulator;

import org.joml.Matrix4f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public final class LogicSimulator extends GLFWManager {

	private Texture currentTexture;
	private ShaderProgram orthograhpicProgram;

	VertexObject squareVertex;

	public void run() {
		System.out.println("LWJGL " + Version.getVersion());

		// Initialize
		initialize();

		GL.createCapabilities();
		glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Program and Shaders
		orthograhpicProgram = new ShaderProgram(ResourceLoader.readAllLines("/simple.vsh"), ResourceLoader.readAllLines("/simple.fsh"));

		// Vertex Buffer Object and Vertex Array Object
		squareVertex = new VertexObject(
				new float[] {
						// pos      // tex
						0.0F, 1.0F, 0.0F, 1.0F,
						1.0F, 0.0F, 1.0F, 0.0F,
						0.0F, 0.0F, 0.0F, 0.0F,

						0.0F, 1.0F, 0.0F, 1.0F,
						1.0F, 1.0F, 1.0F, 1.0F,
						1.0F, 0.0F, 1.0F, 0.0F,
				},
				4
		);

		// Textures
		Textures.init();

		// Loop
		loop();

		// Exit
		exit();
	}

	private int tickClock;
	private boolean paused;

	private void loop() {
		do {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			if(!paused) {
				if(++tickClock == 6) {
					//grid.tick();
					tickClock = 0;
				}
			}

			//grid.updateState();

			//grid.render();

			swapBuffers();
			pollEvents();
		} while(!shouldClose());
	}

	void prepareDrawTexture(Texture texture) {
		currentTexture = texture;

		glUseProgram(orthograhpicProgram.program);

		//

		glUniform1i(glGetUniformLocation(orthograhpicProgram.program, "image"), 0);

		//

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, currentTexture.id);

		glBindVertexArray(squareVertex.vao);
	}

	void drawTextureExact(float xPosition, float yPosition, float xScale, float yScale) {
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram.program, "model"), false,
				new Matrix4f().translate(xPosition, yPosition, 0)
						.scale(currentTexture.width * xScale, currentTexture.height * yScale, 0)
						.get(new float[16])
		);
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram.program, "projection"), false,
				new Matrix4f().ortho(0, windowWidth, windowHeight, 0, -1, 1)
						.get(new float[16])
		);

		glDrawArrays(GL_TRIANGLES, 0, 6);

//		glBindTexture(GL_TEXTURE_2D, 0);
//		glBindVertexArray(0);
	}

	void drawTextureExactPosition(float xExact, float yExact) {
		drawTextureExact(xExact, yExact, 1, 1);
	}

	void drawTextureRatioPosition(float xGridRatio, float yGridOffset) {
		drawTextureExactPosition(xGridRatio * windowWidth, yGridOffset * windowHeight);
	}

	@Override
	void keyCallback(long window, int key, int scancode, int action, int mods) {
		if(key == GLFW.GLFW_KEY_P && action == 1) {
			paused = !paused;
		}
	}

	@Override
	void mouseButtonCallback(long window, int button, int action, int mods) {
//		if(action == GLFW.GLFW_PRESS) {
		updateCursorPosition();

		//final ComponentContainer target = grid.getComponentContainerAt((int) cursorX / 32, (int) cursorY / 32);

		if(GLFW.glfwGetMouseButton(window, 0) == 1) {
			//
		} else if(GLFW.glfwGetMouseButton(window, 1) == 1) {
			//
		}
//		}
	}

	// Static

	public static void main(String[] args) {
		new LogicSimulator().run();
	}
}
