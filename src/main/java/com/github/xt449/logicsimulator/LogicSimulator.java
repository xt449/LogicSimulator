package com.github.xt449.logicsimulator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public final class LogicSimulator extends Application {

	private final ComponentGridPane grid = new ComponentGridPane(32, 32, 40, 20);
	private final GridPane drawer = new GridPane();
	private final VBox root = new VBox(grid, drawer);

	@Override
	public void init() throws Exception {
		// Initialize
		LogicSimulator.instance = this;

		grid.getComponentContainerAt(2, 4).component = new SwitchComponent();

		// Grid
		/*grid.setBackground(new Background(new BackgroundImage(Textures.CELL, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, null, null)));
		grid.setMinHeight(640);
		grid.setPrefHeight(640);
		grid.setMaxHeight(640);

		for(int x = 0; x < 40; x++) {
			grid.getColumnConstraints().add(new ColumnConstraints(32, 32, 32));
		}
		for(int y = 0; y < 20; y++) {
			grid.getRowConstraints().add(new RowConstraints(32, 32, 32));
		}
		// TODO - grid.setGridLinesVisible(true);

		grid.setOnMouseClicked(event -> {
		});
//		grid.add(new ImageView(Textures.BRIDGE_BOTH), 0, 0, 1, 1);
//		grid.add(new ImageView(Textures.BRIDGE_BOTH), 1, 3, 1, 1);
//		grid.add(new ImageView(Textures.BRIDGE_BOTH), 3, 1, 1, 1);
		grid.add(new ImageView(Textures.SWITCH_OFF), 10, 10, 1, 1);
		//grid.*/
		//grid.

		drawer.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		drawer.setMinHeight(80);
		drawer.setPrefHeight(80);
		drawer.setMaxHeight(80);

		// Setup root container
		root.setAlignment(Pos.TOP_LEFT);
		grid.layout();
		root.layout();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(root, 1280, 720));
		primaryStage.setTitle("Logic Simulator");
		primaryStage.setResizable(false);
		primaryStage.show();

		//grid.getChildren().add(new ImageView(Textures.SWITCH_OFF));

		grid.requestLayout();
		grid.layout();
		grid.layoutChildren();

		//System.out.println(grid.getHeight());

		// Loop
		loop();
	}

	@Override
	public void stop() throws Exception {
		// Stop
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

	// Static

	public static void main(String[] args) {
		launch(args);
	}

	static LogicSimulator instance;
}
