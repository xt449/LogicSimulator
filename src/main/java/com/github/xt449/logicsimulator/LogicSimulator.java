package com.github.xt449.logicsimulator;

import org.joml.Matrix4f;
import org.joml.Vector2i;
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

	private ShaderProgram simpleSquareProgram;
	private VertexObject simpleSquareVertex;

	private ShaderProgram scale9SliceProgram;
	private VertexObject scale9SliceVertex;

	private ShaderProgram lineProgram;
	private VertexObject lineVertex;

	public void run() {
		System.out.println("LWJGL " + Version.getVersion());

		// Initialize
		initialize();

		GL.createCapabilities();

		// Enabled 2D Textures
		glEnable(GL_TEXTURE_2D);

		// Enabled Transparent Textures Blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Enable Smooth Lines
		glEnable(GL_LINE_SMOOTH);

		// ShaderProgram: Simple Square
		simpleSquareProgram = new ShaderProgram(ResourceLoader.readAllLines("/simple.vsh"), ResourceLoader.readAllLines("/simple.fsh"));
		simpleSquareVertex = new VertexObject(
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

		// ShaderProgram: Scale 9 Slice
		scale9SliceProgram = new ShaderProgram(ResourceLoader.readAllLines("/scale9slice.vsh"), ResourceLoader.readAllLines("/scale9slice.fsh"));
		scale9SliceVertex = new VertexObject(
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

		// ShaderProgram: Line
		lineProgram = new ShaderProgram(ResourceLoader.readAllLines("/line.vsh"), ResourceLoader.readAllLines("/line.fsh"));
		lineVertex = new VertexObject(
				new float[] {
						// pos
						0.0F, 0.0F,
						0.0F, 0.0F,
				},
				2
		);

		// Textures
		Textures.init();

		// Fonts
		Fonts.init();

		// Loop
		loop();

		// Exit
		exit();
	}

	private int tickClock;
	private boolean paused;

	private void loop() {
		final NodeContainer testContainer = new Inverter(new Vector2i(20, 20));

		do {
			glClearColor(1, 1, 1, 1);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			if(!paused) {
				if(++tickClock == 6) {
					//grid.tick();
					tickClock = 0;
				}
			}

			//grid.updateState();

			//grid.render();
			testContainer.render(this);

			swapBuffers();
			pollEvents();
		} while(!shouldClose());
	}

	private final float[] orthoProjection = new Matrix4f().ortho(0, windowWidth, windowHeight, 0, -1, 1).get(new float[16]);

	void prepareLine(float red, float green, float blue) {
		glUseProgram(lineProgram.program);

		//

		glUniform3f(glGetUniformLocation(lineProgram.program, "color"), red, green, blue);

		//

		glBindVertexArray(lineVertex.vao);
	}

	void drawLine(float xPosition, float yPosition, float xEnd, float yEnd) {
		glUniformMatrix4fv(glGetUniformLocation(lineProgram.program, "model"), false,
				new Matrix4f().translate(xPosition, yPosition, 0)
						.get(new float[16])
		);
		glUniformMatrix4fv(glGetUniformLocation(lineProgram.program, "projection"), false, orthoProjection);

		glBindBuffer(GL_ARRAY_BUFFER, lineVertex.vbo);
		glBufferData(GL_ARRAY_BUFFER, new float[] {0, 0, xEnd - xPosition, yEnd - yPosition}, GL_STATIC_DRAW);

		glLineWidth(4);
		glDrawArrays(GL_LINES, 0, 2);
	}

	void prepare9SliceTexture(Texture texture, float red, float green, float blue) {
		currentTexture = texture;

		glUseProgram(scale9SliceProgram.program);

		//

		glUniform1i(glGetUniformLocation(scale9SliceProgram.program, "image"), 0);
		glUniform3f(glGetUniformLocation(scale9SliceProgram.program, "color"), red, green, blue);

		//

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, currentTexture.id);

		glBindVertexArray(scale9SliceVertex.vao);
	}

	void draw9SliceTexture(float xPosition, float yPosition, int width, int height) {
		glUniformMatrix4fv(glGetUniformLocation(scale9SliceProgram.program, "model"), false,
				new Matrix4f().translate(xPosition, yPosition, 0)
						.scale(width, height, 0)
						.get(new float[16])
		);
		glUniformMatrix4fv(glGetUniformLocation(scale9SliceProgram.program, "projection"), false, orthoProjection);

		glDrawArrays(GL_TRIANGLES, 0, 6);
	}

	void prepareSimpleTexture(Texture texture, float red, float green, float blue, boolean mirrored, boolean flipped, boolean inverted) {
		currentTexture = texture;

		glUseProgram(simpleSquareProgram.program);

		//

		glUniform1i(glGetUniformLocation(simpleSquareProgram.program, "image"), 0);
		glUniform3f(glGetUniformLocation(simpleSquareProgram.program, "color"), red, green, blue);
		glUniform1i(glGetUniformLocation(simpleSquareProgram.program, "mirror"), mirrored ? 1 : 0);
		glUniform1i(glGetUniformLocation(simpleSquareProgram.program, "flip"), flipped ? 1 : 0);
		glUniform1i(glGetUniformLocation(simpleSquareProgram.program, "invert"), inverted ? 1 : 0);

		//

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, currentTexture.id);

		glBindVertexArray(simpleSquareVertex.vao);
	}

	/*void drawSimpleTextureExact(float xPosition, float yPosition, float xScale, float yScale) {
		glUniformMatrix4fv(glGetUniformLocation(simpleSquareProgram.program, "model"), false,
				new Matrix4f().translate(xPosition, yPosition, 0)
						.scale(currentTexture.width * xScale, currentTexture.height * yScale, 0)
						.get(new float[16])
		);
		glUniformMatrix4fv(glGetUniformLocation(simpleSquareProgram.program, "projection"), false,
				new Matrix4f().ortho(0, windowWidth, windowHeight, 0, -1, 1)
						.get(new float[16])
		);

		glDrawArrays(GL_TRIANGLES, 0, 6);
	}*/

	void drawSimpleTextureExact(float xPosition, float yPosition) {
		glUniformMatrix4fv(glGetUniformLocation(simpleSquareProgram.program, "model"), false,
				new Matrix4f().translate(xPosition, yPosition, 0)
						.scale(currentTexture.width, currentTexture.height, 0)
						.get(new float[16])
		);
		glUniformMatrix4fv(glGetUniformLocation(simpleSquareProgram.program, "projection"), false, orthoProjection);

		glDrawArrays(GL_TRIANGLES, 0, 6);
	}

	void drawSimpleTextureExactPosition(float xExact, float yExact) {
		drawSimpleTextureExact(xExact, yExact);
	}

	void drawSimpleTextureRatioPosition(float xGridRatio, float yGridOffset) {
		drawSimpleTextureExactPosition(xGridRatio * windowWidth, yGridOffset * windowHeight);
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
