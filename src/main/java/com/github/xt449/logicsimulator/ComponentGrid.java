package com.github.xt449.logicsimulator;

import static org.lwjgl.opengl.GL33C.*;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class ComponentGrid {

	private final LogicSimulator simulator;
	private final int gridWidth;
	private final int gridHeight;

	private final ComponentContainer[][] containerGrid;

	public ComponentGrid(LogicSimulator simulator, int gridWidth, int gridHeight) {
		this.simulator = simulator;

		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;

		this.containerGrid = new ComponentContainer[gridWidth][gridHeight];

		// Populate Grid
		for(int y = 0; y < gridHeight; y++) {
			for(int x = 0; x < gridWidth; x++) {
				this.containerGrid[x][y] = new ComponentContainer(simulator, x, y);
			}
		}
	}

	ComponentContainer getComponentContainerAt(int x, int y) {
		if(x < 0 || x >= gridWidth || y < 0 || y >= gridHeight) {
			return null;
		}

		return containerGrid[x][y];
	}

	Component getComponentAt(int x, int y) {
		if(x < 0 || x >= gridWidth || y < 0 || y >= gridHeight) {
			return null;
		}

		return containerGrid[x][y].component;
	}

	void tick() {
		for(int y = 0; y < gridHeight; y++) {
			for(int x = 0; x < gridWidth; x++) {
				containerGrid[x][y].tick();
			}
		}
	}

	void updateState() {
		for(int y = 0; y < gridHeight; y++) {
			for(int x = 0; x < gridWidth; x++) {
				containerGrid[x][y].updateState();
			}
		}
	}

	void render() {
		// Prepare to draw entire grid
		glBindBuffer(GL_ARRAY_BUFFER, simulator.squareVertex.vbo);
		glBufferData(GL_ARRAY_BUFFER, new float[] {
				0.0F, 1.0F, 0.0F, gridHeight,
				1.0F, 0.0F, gridWidth, 0.0F,
				0.0F, 0.0F, 0.0F, 0.0F,

				0.0F, 1.0F, 0.0F, gridHeight,
				1.0F, 1.0F, gridWidth, gridHeight,
				1.0F, 0.0F, gridWidth, 0.0F,
		}, GL_STATIC_DRAW);
		// Draw entire grid
		simulator.prepareDrawTexture(Textures.CELL);
		simulator.drawTextureExact(0, 0, gridWidth, gridHeight);

		// Prepare to draw normal single cell textures
		glBindBuffer(GL_ARRAY_BUFFER, simulator.squareVertex.vbo);
		glBufferData(GL_ARRAY_BUFFER, simulator.vertices, GL_STATIC_DRAW);

		for(int y = 0; y < gridHeight; y++) {
			for(int x = 0; x < gridWidth; x++) {
				containerGrid[x][y].render();
			}
		}
	}
}
