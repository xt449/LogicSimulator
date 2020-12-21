package com.github.xt449.logicsimulator;

import java.util.Collection;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public interface NodeContainer {
	Collection<Node> getNodes();

	void render();
}
