package com.github.xt449.logicsimulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
abstract class ResourceLoader {

	static String readAllLines(String resourceLocation) {
		return new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(resourceLocation))).lines().collect(Collectors.joining("\n"));
	}
}
