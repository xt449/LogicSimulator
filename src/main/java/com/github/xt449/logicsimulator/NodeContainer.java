package com.github.xt449.logicsimulator;

import org.joml.Vector2i;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public interface NodeContainer {

	/**
	 * @return nodes belonging to this container
	 */
	Node[] getNodes();

	/**
	 * @return absolute position
	 */
	Vector2i getPosition();

	void render();

	interface Node {

		NodeContainer getParent();

		/**
		 * @return true if output node, false if input node
		 */
		boolean isOutput();

		/**
		 * @return position relative to parent
		 */
		Vector2i getLocalPosition();

		/**
		 * @return absolute position
		 */
		Vector2i getGlobalPosition();
	}
}
