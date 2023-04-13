package com.GUI

import com.State.StateManager
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil
import kotlin.properties.Delegates


class Window(private val width : Int, private val height : Int, private var title : String) {
    private var window by Delegates.notNull<Long>()
    private val stateManager : StateManager = StateManager()

    fun run() {
        init()
        loop()
        cleanup()
    }

    private fun init() {

        // Initialize GLFW and create a window
        if (!glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        window = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) {
            throw RuntimeException("Failed to create GLFW window")
        }

        // Set up an error callback
        GLFWErrorCallback.createPrint(System.err).set()

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(
            window
        ) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(
                window,
                true
            ) // We will detect this in the rendering loop
        }

        // Get the thread stack and push a new frame
        stackPush().use { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())

            // Center the window
            glfwSetWindowPos(
                window,
                (vidmode!!.width() - pWidth[0]) / 2,
                (vidmode!!.height() - pHeight[0]) / 2
            )
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window)
        // Enable VSync
        glfwSwapInterval(1)
        // Make the window visible
        glfwShowWindow(window)



    }


    private fun loop() {
        while (!glfwWindowShouldClose(window)) {
            // Process input
            glfwPollEvents()
            stateManager.handleInput()

            // Update the current state
            stateManager.update(0f)

            // Clear the screen
            glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
            glClear(GL_COLOR_BUFFER_BIT)

            // Render the current state
            stateManager.render()

            // Swap the buffers
            glfwSwapBuffers(window)
        }
    }

    private fun cleanup() {
        // Clean up resources and dispose of the current states
        stateManager.dispose()

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

}