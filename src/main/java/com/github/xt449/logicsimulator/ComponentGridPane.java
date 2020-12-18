package com.github.xt449.logicsimulator;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class ComponentGridPane extends Pane {

	private final int cellWidth;
	private final int cellHeight;

	private final int gridWidth;
	private final int gridHeight;

	private final ComponentContainer[][] containerGrid;

	public ComponentGridPane(int cellWidth, int cellHeight, int gridWidth, int gridHeight) {
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;

		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;

		this.containerGrid = new ComponentContainer[gridWidth][gridHeight];

		for(int y = 0; y < gridHeight; y++) {
			for(int x = 0; x < gridWidth; x++) {
				getChildren().add(this.containerGrid[x][y] = new ComponentContainer(this, x, y));
			}
		}

		this.setBackground(new Background(new BackgroundImage(Textures.CELL, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, null, null)));

		final int width = cellWidth * gridWidth;
		this.setMinWidth(width);
		this.setPrefWidth(width);
		this.setMaxWidth(width);

		final int height = cellHeight * gridHeight;
		this.setMinHeight(height);
		this.setPrefHeight(height);
		this.setMaxHeight(height);
	}

	@Override
	protected void layoutChildren() {
		for(int y = 0; y < gridHeight; y++) {
			for(int x = 0; x < gridWidth; x++) {
				layoutInArea(containerGrid[x][y], x * cellWidth, y * cellHeight, cellWidth, cellHeight, 0, HPos.CENTER, VPos.CENTER);
			}
		}
	}

	ComponentContainer getComponentContainerAt(int x, int y) {
		if(x < 0 || x >= gridWidth || y < 0 || y >= gridHeight) {
			return null;
		}

		return containerGrid[x][y];
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
}
