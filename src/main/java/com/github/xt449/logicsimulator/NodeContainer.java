package com.github.xt449.logicsimulator;

import org.joml.Vector2i;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public abstract class NodeContainer {

	final Vector2i position;

	final int width;
	final int height;

	public NodeContainer(Vector2i position, int width, int height) {
		this.position = position;

		this.width = width;
		this.height = height;
	}

	/**
	 * @return nodes belonging to this container
	 */
	protected abstract Node[] getNodes();

	protected abstract void tick();

	protected void postTick() {
	}

	public final void render(LogicSimulator simulator) {
		// Render Container

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

	protected boolean containsPoint(Vector2i point) {
		return (position.x <= point.x && position.y <= point.y) && (position.x + width >= point.x && position.y + height >= point.y);
	}
}
