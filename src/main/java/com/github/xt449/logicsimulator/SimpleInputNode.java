package com.github.xt449.logicsimulator;

import org.joml.Vector2i;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class SimpleInputNode extends AbstractSimpleNode {

	SimpleInputNode(NodeContainer parent, Vector2i localPosition) {
		super(parent, localPosition);
	}

	@Override
	public boolean isOutput() {
		return false;
	}
}