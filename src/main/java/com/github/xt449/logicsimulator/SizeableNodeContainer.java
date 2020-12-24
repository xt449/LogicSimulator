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
	public final void render() {
		final LogicSimulator simulator = LogicSimulator.get();

		Vector2i position;

		// Box Render

		position = getPosition();

		simulator.prepare9SliceTexture(Textures.BOX_9SLICE, 1, 1, 1);
		simulator.draw9SliceTexture(position.x, position.y, width, height);

		// Node Render

		final Node[] nodes = getNodes();

		for(int i = 0; i < nodes.length; i++) {
			position = nodes[i].getGlobalPosition();
			if(nodes[i].isOutput()) {
				simulator.prepareSimpleTexture(Textures.NODE_OUTPUT, 1, 1, 1, false, false, false);
			} else {
				simulator.prepareSimpleTexture(Textures.NODE_INPUT, 1, 1, 1, false, false, false);
			}

			simulator.drawSimpleTextureExactPosition(position.x, position.y);
		}

		postRender();
	}

	protected void postRender() {
	}
}