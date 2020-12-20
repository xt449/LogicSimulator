package com.github.xt449.logicsimulator;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
abstract class GLFWManager {

	final int initialWidth = 1280; //32 * 16/* * 3*/;
	final int initialHeight = 720; //32 * 9/* * 3*/;

	int windowWidth = initialWidth;
	int windowHeight = initialHeight;

	double cursorX = 0;
	double cursorY = 0;

	private long window;

	void initialize() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if(!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// Configure GLFW
		GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // the window will stay hidden after creation
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE); // the window will NOT be resizable

		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);

		// Create the window
		window = GLFW.glfwCreateWindow(initialWidth, initialHeight, "Logic Simulator", 0, 0);
		if(window == 0) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		GLFW.glfwSetKeyCallback(window, this::keyCallback);

		// Setup a mouse button callback. It will be called every time a mouse button is pressed or released.
		GLFW.glfwSetMouseButtonCallback(window, this::mouseButtonCallback);

		// Setup more callbacks...
//		GLFW.glfwSetWindowSizeCallback(window, this::windowSizeCallback);

		// Get the thread stack and push a new frame
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer widthPointer = stack.mallocInt(1);
			IntBuffer heightPointer = stack.mallocInt(1);

			// Get the window size passed to glfwCreateWindow
			GLFW.glfwGetWindowSize(window, widthPointer, heightPointer);

			windowWidth = widthPointer.get(0);
			windowHeight = heightPointer.get(0);

			// Get the resolution of the primary monitor
			GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

			// Center the window
			GLFW.glfwSetWindowPos(window,
					(videoMode.width() - windowWidth) / 2,
					(videoMode.height() - windowHeight) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		GLFW.glfwMakeContextCurrent(window);

		// Enable v-sync
		GLFW.glfwSwapInterval(1);

		// Make the window visible
		GLFW.glfwShowWindow(window);
	}

	void exit() {
		// Free the window callbacks and destroy the window
		Callbacks.glfwFreeCallbacks(window);
		GLFW.glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}

	void swapBuffers() {
		// swap the color buffers
		GLFW.glfwSwapBuffers(window);
	}

	void pollEvents() {
		// Poll for window events. The key callback above will only be
		// invoked during this call.
		GLFW.glfwPollEvents();
	}

	boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}

	/**
	 * Will be called when a key is pressed, repeated or released.
	 *
	 * @param window   the window that received the event
	 * @param key      the keyboard key that was pressed or released
	 * @param scancode the system-specific scancode of the key
	 * @param action   the key action. One of:<br><table><tr><td>{@link GLFW#GLFW_PRESS PRESS}</td><td>{@link GLFW#GLFW_RELEASE RELEASE}</td><td>{@link GLFW#GLFW_REPEAT REPEAT}</td></tr></table>
	 * @param mods     bitfield describing which modifiers keys were held down
	 */
	abstract void keyCallback(long window, int key, int scancode, int action, int mods);

	/**
	 * Will be called when a mouse button is pressed or released.
	 *
	 * @param window the window that received the event
	 * @param button the mouse button that was pressed or released
	 * @param action the button action. One of:<br><table><tr><td>{@link GLFW#GLFW_PRESS PRESS}</td><td>{@link GLFW#GLFW_RELEASE RELEASE}</td><td>{@link GLFW#GLFW_REPEAT REPEAT}</td></tr></table>
	 * @param mods   bitfield describing which modifiers keys were held down
	 */
	abstract void mouseButtonCallback(long window, int button, int action, int mods);

//	/**
//	 * Will be called when the specified window is resized.
//	 *
//	 * @param window the window that was resized
//	 * @param width  the new width, in screen coordinates, of the window
//	 * @param height the new height, in screen coordinates, of the window
//	 */
//	void windowSizeCallback(long window, int width, int height) {
//		windowWidth = width;
//		windowHeight = height;
//	}

	void updateCursorPosition() {
		final double[] x = new double[1];
		final double[] y = new double[1];
		GLFW.glfwGetCursorPos(window, x, y);
		cursorX = x[0];
		cursorY = y[0];
	}
}
