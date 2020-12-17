package com.github.xt449.logicsimulator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public final class LogicSimulator extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	static LogicSimulator instance;

	// Instance

	private final int GRID_WIDTH = 40;
	private final int GRID_HEIGHT = 20;
	private final GridComponentContainer[][] containerGrid = new GridComponentContainer[GRID_WIDTH][GRID_HEIGHT];

	//private Texture currentTexture;

	private final GridPane grid = new GridPane();
	private final GridPane drawer = new GridPane();
	private final VBox root = new VBox(grid, drawer);

	@Override
	public void init() throws Exception {
		// Initialize
		LogicSimulator.instance = this;

		// Grid Background
		grid.setBackground(new Background(new BackgroundImage(new Image(LogicSimulator.class.getResourceAsStream("/textures/cell.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, null, null)));
		grid.setMinHeight(640);
		grid.setPrefHeight(640);
		grid.setMaxHeight(640);

		drawer.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		drawer.setMinHeight(80);
		drawer.setPrefHeight(80);
		drawer.setMaxHeight(80);

		// Setup root container
		root.setAlignment(Pos.TOP_LEFT);
		grid.layout();
		root.layout();

		// Populate Grid
		for(int y = 0; y < GRID_HEIGHT; y++) {
			for(int x = 0; x < GRID_WIDTH; x++) {
				containerGrid[x][y] = new GridComponentContainer(x, y);
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(root, 1280, 720));
		primaryStage.setTitle("Logic Simulator");
		primaryStage.setResizable(false);
		primaryStage.show();

		System.out.println(grid.getHeight());

		// Loop
		loop();
	}

	@Override
	public void stop() throws Exception {
		// Stop
	}

	GridComponentContainer getComponentContainerAt(int x, int y) {
		if(x < 0 || x >= GRID_WIDTH || y < 0 || y >= GRID_HEIGHT) {
			return null;
		}

		return containerGrid[x][y];
	}

	GridComponent getComponentAt(int x, int y) {
		if(x < 0 || x >= GRID_WIDTH || y < 0 || y >= GRID_HEIGHT) {
			return null;
		}

		return containerGrid[x][y].component;
	}

	private int tickClock;
	private boolean paused;

	private void loop() {
		/*do {
			if(!paused) {
				if(++tickClock == 6) {
					tick();
					tickClock = 0;
				}
			}

			render();

			swapBuffers();
			pollEvents();
		} while(!shouldClose());*/
	}

	private void tick() {
		for(int y = 0; y < GRID_HEIGHT; y++) {
			for(int x = 0; x < GRID_WIDTH; x++) {
				containerGrid[x][y].tick();
			}
		}
		for(int y = 0; y < GRID_HEIGHT; y++) {
			for(int x = 0; x < GRID_WIDTH; x++) {
				containerGrid[x][y].updateState();
			}
		}
	}

	private void render() {
		// Draw entire grid
		/*prepareDrawTexture(Textures.CELL);
		drawTextureExact(0, 0, GRID_WIDTH, GRID_HEIGHT);*/

		// Render components on grid
		for(int y = 0; y < GRID_HEIGHT; y++) {
			for(int x = 0; x < GRID_WIDTH; x++) {
				containerGrid[x][y].render();
			}
		}
	}

	/*@Override
	void keyCallback(long window, int key, int scancode, int action, int mods) {
		if(key == GLFW.GLFW_KEY_P && action == 1) {
			paused = !paused;
		}
	}

	@Override
	void mouseButtonCallback(long window, int button, int action, int mods) {
		updateCursorPosition();

		final GridComponentContainer target = getComponentContainerAt((int) cursorX / 32, (int) cursorY / 32);

		if(GLFW.glfwGetMouseButton(window, 0) == 1) {
			if(target != null) {
				if(target.component == null) {
					target.component = new WireComponent();
				} else if(target.component instanceof WireComponent) {
					target.component = new InverterComponent();
				} else if(target.component instanceof InverterComponent) {
					target.component = new DiodeComponent();
				} else if(target.component instanceof DiodeComponent) {
					target.component = new SwitchComponent();
				} else if(target.component instanceof SwitchComponent) {
					target.component = new BridgeComponent();
				} else if(target.component instanceof BridgeComponent) {
					target.component = null;
				}
			}
		} else if(GLFW.glfwGetMouseButton(window, 1) == 1) {
			if(target != null) {
				if(target.component instanceof InteractableComponent) {
					((InteractableComponent) target.component).interact();
				}
			}
		}
	}*/
}
