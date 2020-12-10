package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class WireComponent extends GridComponent {

	@Override
	boolean redirectsWireFrom(int direction) {
		return true;
	}
}
