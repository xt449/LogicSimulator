package com.github.xt449.logicsimulator;

import org.joml.Matrix4f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public final class LogicSimulator extends GLFWManager {

	public static void main(String[] args) {
		new LogicSimulator().run();
	}

	static LogicSimulator instance;

	static final int GRID_WIDTH = 40;
	static final int GRID_HEIGHT = 20;
	static GridSquare[][] grid = new GridSquare[GRID_WIDTH][GRID_HEIGHT];

	static GridSquare getGridSquare(int x, int y) {
		if(x < 0 || x >= LogicSimulator.GRID_WIDTH || y < 0 || y >= LogicSimulator.GRID_HEIGHT) {
			return null;
		}

		return LogicSimulator.grid[x][y];
	}

	static GridComponent getGridComponent(int x, int y) {
		if(x < 0 || x >= LogicSimulator.GRID_WIDTH || y < 0 || y >= LogicSimulator.GRID_HEIGHT) {
			return null;
		}

		return LogicSimulator.grid[x][y].component;
	}

	private static Texture currentTexture;

	int orthograhpicProgram;
	int vbo;
	int vao;

	public void run() {
		System.out.println("LWJGL " + Version.getVersion());

		LogicSimulator.instance = this;
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
//		glUseProgram(orthograhpicProgram);

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

		// Populate Grid
		for(int y = 0; y < GRID_HEIGHT; y++) {
			for(int x = 0; x < GRID_WIDTH; x++) {
				grid[x][y] = new GridSquare(x, y);
			}
		}

		// Exmaple Grid
		grid[0][0].component = new InverterComponent(Direction.RIGHT);
		grid[0][1].component = new InverterComponent(Direction.RIGHT);
		grid[0][2].component = new InverterComponent(Direction.RIGHT);
		grid[0][3].component = new InverterComponent(Direction.RIGHT);

		grid[1][0].component = new WireComponent();
		grid[1][1].component = new WireComponent();
		grid[1][2].component = new WireComponent();
		grid[1][3].component = new WireComponent();

		grid[2][0].component = new InverterComponent(Direction.RIGHT);

		grid[3][0].component = new WireComponent();

		// TODO: Debug
		System.out.println(Direction.getDirectionReversed(Direction.UP));
		System.out.println(Direction.getDirectionReversed(Direction.DOWN));
		System.out.println(Direction.getDirectionReversed(Direction.LEFT));
		System.out.println(Direction.getDirectionReversed(Direction.RIGHT));

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

			tick();

			render();

			swapBuffers();
			pollEvents();
		} while(!shouldClose());
	}

	private void tick() {
		for(int y = 0; y < GRID_HEIGHT; y++) {
			for(int x = 0; x < GRID_WIDTH; x++) {
				grid[x][y].tick();
			}
		}
	}

	private void render() {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, new float[] {
				0.0F, 1.0F, 0.0F, GRID_HEIGHT,
				1.0F, 0.0F, GRID_WIDTH, 0.0F,
				0.0F, 0.0F, 0.0F, 0.0F,

				0.0F, 1.0F, 0.0F, GRID_HEIGHT,
				1.0F, 1.0F, GRID_WIDTH, GRID_HEIGHT,
				1.0F, 0.0F, GRID_WIDTH, 0.0F,
		}, GL_STATIC_DRAW);
		// Draw entire grid
		prepareDrawTexture(Texture.CELL);
		drawTextureExact(0, 0, GRID_WIDTH, GRID_HEIGHT);

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		//

		for(int y = 0; y < GRID_HEIGHT; y++) {
			for(int x = 0; x < GRID_WIDTH; x++) {
				grid[x][y].render();
			}
		}

		//

		/*prepareDrawTexture(Texture.WIRE_CENTER);
		drawTextureGridPosition(3, 0);

		prepareDrawTexture(Texture.WIRE_UP);

		prepareDrawTexture(Texture.WIRE_DOWN);

		prepareDrawTexture(Texture.WIRE_LEFT);
		drawTextureGridPosition(0, 0);
		drawTextureGridPosition(0, 1);
		drawTextureGridPosition(0, 2);
		drawTextureGridPosition(0, 3);
		drawTextureGridPosition(3, 0);

		prepareDrawTexture(Texture.WIRE_RIGHT);
		drawTextureGridPosition(2, 0);

		//

		prepareDrawTexture(Texture.WIRE_CENTER_POWERED);
		drawTextureGridPosition(1, 0);
		drawTextureGridPosition(1, 1);
		drawTextureGridPosition(1, 2);
		drawTextureGridPosition(1, 3);

		prepareDrawTexture(Texture.WIRE_UP_POWERED);
		drawTextureGridPosition(1, 1);
		drawTextureGridPosition(1, 2);
		drawTextureGridPosition(1, 3);

		prepareDrawTexture(Texture.WIRE_DOWN_POWERED);
		drawTextureGridPosition(1, 0);
		drawTextureGridPosition(1, 1);
		drawTextureGridPosition(1, 2);

		prepareDrawTexture(Texture.WIRE_LEFT_POWERED);
		drawTextureGridPosition(1, 0);
		drawTextureGridPosition(1, 1);
		drawTextureGridPosition(1, 2);
		drawTextureGridPosition(1, 3);
		drawTextureGridPosition(2, 0);

		prepareDrawTexture(Texture.WIRE_RIGHT_POWERED);
		drawTextureGridPosition(0, 0);
		drawTextureGridPosition(0, 1);
		drawTextureGridPosition(0, 2);
		drawTextureGridPosition(0, 3);
		drawTextureGridPosition(1, 0);

		//

		prepareDrawTexture(Texture.INVERTER_UP);

		prepareDrawTexture(Texture.INVERTER_DOWN);

		prepareDrawTexture(Texture.INVERTER_LEFT);

		prepareDrawTexture(Texture.INVERTER_RIGHT);
		drawTextureGridPosition(0, 0);
		drawTextureGridPosition(0, 1);
		drawTextureGridPosition(0, 2);
		drawTextureGridPosition(0, 3);

		//

		prepareDrawTexture(Texture.INVERTER_UP_POWERED);

		prepareDrawTexture(Texture.INVERTER_DOWN_POWERED);

		prepareDrawTexture(Texture.INVERTER_LEFT_POWERED);

		prepareDrawTexture(Texture.INVERTER_RIGHT_POWERED);
		drawTextureGridPosition(2, 0);*/

		//

		// TODO - Debug
//      prepareDrawTexture(Texture.WIRE_CENTER_POWERED);
//      drawTextureRatioPosition(0.5F, 0.5F);
	}

	void prepareDrawTexture(Texture texture) {
		currentTexture = texture;

		glUseProgram(orthograhpicProgram);

		//

		glUniform1i(glGetUniformLocation(orthograhpicProgram, "image"), 0);

		//

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, currentTexture.id);

		glBindVertexArray(vao);
	}

	void drawTextureExact(float xPosition, float yPosition, float xScale, float yScale) {
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram, "model"), false,
				new Matrix4f().translate(xPosition, yPosition, 0)
						.scale(currentTexture.width * xScale, currentTexture.height * yScale, 0)
						.get(new float[16])
		);
		glUniformMatrix4fv(glGetUniformLocation(orthograhpicProgram, "projection"), false,
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

	void drawTextureGridPosition(int xGrid, int yGrid) {
		drawTextureExactPosition(xGrid * currentTexture.width, yGrid * currentTexture.height);
	}

	void drawTextureRatioPosition(float xGridRatio, float yGridOffset) {
		drawTextureExactPosition(xGridRatio * windowWidth, yGridOffset * windowHeight);
	}

	@Override
	void keyCallback(long window, int key, int scancode, int action, int mods) {

	}

	@Override
	void mouseButtonCallback(long window, int button, int action, int mods) {

		//if(action == GLFW.GLFW_PRESS) {
		updateCursorPosition();

		final GridSquare target = getGridSquare((int) cursorX / 32, (int) cursorY / 32);

		if(GLFW.glfwGetMouseButton(window, 0) == 1) {
			if(target != null) {
				if(target.component == null) {
					target.component = new WireComponent();
				} else if(target.component instanceof WireComponent) {
					target.component = new InverterComponent();
				} else if(target.component instanceof InverterComponent) {
					target.component = null;
				}
			}
		} else if(GLFW.glfwGetMouseButton(window, 1) == 1) {
			if(target != null) {
				if(target.component instanceof Directional) {
					((Directional) target.component).rotate();
				}
			}
		}
		//}
	}
}
