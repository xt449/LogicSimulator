package com.github.xt449.logicsimulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
abstract class ResourceLoader {

	static String readAllLines(String resourceLocation) {
		return new BufferedReader(new InputStreamReader(LogicSimulator.class.getResourceAsStream(resourceLocation))).lines().collect(Collectors.joining("\n"));
	}
}
