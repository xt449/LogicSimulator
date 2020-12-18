package com.github.xt449.logicsimulator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public final class LogicSimulator extends Application {

	private final ComponentGridPane grid = new ComponentGridPane(32, 32, 40, 20);
	private final GridPane drawer = new GridPane();
	private final VBox root = new VBox(grid, drawer);

	private boolean running;
	private boolean paused;
	private long tickClock;

	private final ExecutorService executorService = Executors.newFixedThreadPool(1);

	@Override
	public void init() {
		grid.getComponentContainerAt(0, 0).component = new SwitchComponent();
		grid.getComponentContainerAt(0, 0).updateState();

		drawer.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		drawer.setMinHeight(80);
		drawer.setPrefHeight(80);
		drawer.setMaxHeight(80);

		// Setup root container
		//root.setAlignment(Pos.TOP_LEFT);
		//grid.layout();
		//root.layout();
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene(new Scene(root, 1280, 720));
		primaryStage.setTitle("Logic Simulator");
		primaryStage.setResizable(false);

		// Events
		primaryStage.setOnCloseRequest(event -> running = false);
		root.setOnKeyPressed(this::keyCallback);

		// Show
		primaryStage.show();

		running = true;
		executorService.submit(this::logicLoop);
	}

	@Override
	public void stop() {
		executorService.shutdownNow();
	}

	private void logicLoop() {
		do {
			if(!paused) {
				long time = System.currentTimeMillis();
				if(time - tickClock >= 100) {
					tickClock = time;
					grid.tick();
				}
			}
			grid.updateState();
		} while(running);
	}

	private void keyCallback(KeyEvent event) {
		// TODO
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
}
