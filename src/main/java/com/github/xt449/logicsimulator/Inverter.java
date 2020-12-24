package com.github.xt449.logicsimulator;

import org.joml.Vector2i;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class Inverter extends SizeableNodeContainer {

	final Vector2i position;

	final Node input = new SimpleInputNode(this, new Vector2i(8 - 4, 8 - 4));
	final Node output = new SimpleOutputNode(this, new Vector2i(64 - 4, 8 - 4));

	public Inverter(Vector2i position) {
		super(80, 24);

		this.position = position;
	}

	@Override
	public Node[] getNodes() {
		return new Node[] {input, output};
	}

	@Override
	public Vector2i getPosition() {
		return new Vector2i(position);
	}

	/*@Override
	protected void postRender() {

	}*/
}
