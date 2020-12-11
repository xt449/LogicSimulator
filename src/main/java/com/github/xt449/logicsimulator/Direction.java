package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class Direction {

	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	//  #    #   #   #
	//
	//  #   0,0 1,0 2,0
	//
	//  #   0,1 1,1 2,1
	//
	//  #   0,2 1,2 2,2

	public static int getDirectionRelative(int xFrom, int yFrom, int xTo, int yTo) {
		final int xDifference = xTo - xFrom;
		final int yDifference = yTo - yFrom;

		if(Math.abs(xDifference) > Math.abs(yDifference)) {
			return xDifference > 0 ? RIGHT : LEFT;
		} else {
			return yDifference > 0 ? DOWN : UP;
		}
	}

	public static int getDirectionReversed(int direction) {
		return (direction + 1) % 2 + (direction > 1 ? 2 : 0);
	}

	public static int[] getEach() {
		return new int[] {UP, DOWN, LEFT, RIGHT};
	}
}
