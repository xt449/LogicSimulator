package com.github.xt449.logicsimulator;

import org.joml.Vector2i;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public abstract class AbstractSimpleNode implements Node {

	protected final NodeContainer parent;
	protected final Vector2i localPosition;

	AbstractSimpleNode(NodeContainer parent, Vector2i localPosition) {
		this.parent = parent;
		this.localPosition = localPosition;
	}

	@Override
	public NodeContainer getParent() {
		return parent;
	}

	@Override
	public Vector2i getLocalPosition() {
		return new Vector2i(localPosition);
	}

	@Override
	public Vector2i getGlobalPosition() {
		return new Vector2i(parent.position).add(localPosition);
	}

	@Override
	public void render(LogicSimulator simulator) {
		final Vector2i position = getGlobalPosition();

		if(isOutput()) {
			simulator.prepareSimpleTexture(Textures.NODE_OUTPUT, 1, 1, 1, false, false, false);
		} else {
			simulator.prepareSimpleTexture(Textures.NODE_INPUT, 1, 1, 1, false, false, false);
		}

		// subtract 8 for half the size of the node texture
		simulator.drawSimpleTextureExactPosition(position.x - 8, position.y - 8);

		simulator.prepareLine(1, 0, 0);
		simulator.drawLine(position.x, position.y, 128, 128);
	}
}
