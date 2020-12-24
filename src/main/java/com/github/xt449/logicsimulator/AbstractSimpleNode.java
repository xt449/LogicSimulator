package com.github.xt449.logicsimulator;

import org.joml.Vector2i;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public abstract class AbstractSimpleNode implements NodeContainer.Node {

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
		return parent.getPosition().add(localPosition);
	}
}
