package com.github.xt449.logicsimulator;

import org.joml.Vector2i;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
abstract class GLFWManager {

	final int initialWidth = 1280;
	final int initialHeight = 720;

	final Vector2i windowSize = new Vector2i();

	private long window;

	void init() {
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
		window = GLFW.glfwCreateWindow(initialWidth, initialHeight, "Hello World!", 0, 0);
		if(window == 0) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		/*
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
				GLFW.glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			}
		});
		*/

		// Get the thread stack and push a new frame
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer widthPointer = stack.mallocInt(1);
			IntBuffer heightPointer = stack.mallocInt(1);

			// Get the window size passed to glfwCreateWindow
			GLFW.glfwGetWindowSize(window, widthPointer, heightPointer);

			windowSize.x = widthPointer.get(0);
			windowSize.y = heightPointer.get(0);

			// Get the resolution of the primary monitor
			GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

			// Center the window
			GLFW.glfwSetWindowPos(window,
					(videoMode.width() - windowSize.x) / 2,
					(videoMode.height() - windowSize.y) / 2
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
}
