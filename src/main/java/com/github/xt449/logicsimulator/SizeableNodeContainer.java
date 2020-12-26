package com.github.xt449.logicsimulator;

import org.joml.Vector2i;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public abstract class SizeableNodeContainer implements NodeContainer {

	final int width;
	final int height;

	public SizeableNodeContainer(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public final void render(LogicSimulator simulator) {
		// Render Container

		final Vector2i position = getPosition();

		simulator.prepare9SliceTexture(Textures.BOX_9SLICE, 1, 1, 1);
		simulator.draw9SliceTexture(position.x, position.y, width, height);

		// Render Nodes

		final Node[] nodes = getNodes();

		for(int i = 0; i < nodes.length; i++) {
			nodes[i].render(simulator);
		}

		postRender(simulator);
	}

	protected void postRender(LogicSimulator simulator) {
	}
}
