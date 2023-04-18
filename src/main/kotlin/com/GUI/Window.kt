package com.GUI

import com.Engine.Time
import com.State.GameState
import com.State.State
import com.State.StateManager
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil
import kotlin.properties.Delegates
import kotlin.system.measureNanoTime


class Window(private var width : Int, private var height : Int, title : String) {
    private var title : String = title
        set(value) {
            title = value
            glfwSetWindowTitle(window, title)
        }


    private var window by Delegates.notNull<Long>()
    private val stateManager : StateManager = StateManager()

    private var resize : Boolean = false
    private var vSync : Boolean = true

    // Time
    val NANOSECOND : Float = 0.000000001f
    val BILLION_NANOSECOND : Float = 1_000_000_000f
    val secondsPerFrame : Float = 1f / 60f
    val nanoSecondsPerFrame : Float = secondsPerFrame / BILLION_NANOSECOND

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

        setGlfwConfig()

        window = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)

        if (window == MemoryUtil.NULL) {
            throw RuntimeException("Failed to create GLFW window")
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window)

        setCallbacks()

        // Center the window
        val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!
        glfwSetWindowPos(
            window,
            (vidmode.width() - width) / 2,
            (vidmode.height() - height) / 2
        )

        // Enable VSync
        if (vSync) glfwSwapInterval(1)

        // Make the window visible
        glfwShowWindow(window)

        GL.createCapabilities()
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_STENCIL_TEST)
        glEnable(GL_CULL_FACE)
        glEnable(GL_BACK) // cull backside of objects we can't see

        // Push the initial state onto the state manager
        stateManager.pushState(GameState())

    }


    private fun loop() {

        var startTime: Long
        var previousTime = System.nanoTime()
        var deltaTime: Float

        while (!glfwWindowShouldClose(window)) {

            startTime = System.nanoTime()
            deltaTime = (startTime - previousTime) / BILLION_NANOSECOND

            // we will only update the physics / world at a fixed interval
            if (deltaTime >= secondsPerFrame) {

                deltaTime = (startTime - previousTime) / BILLION_NANOSECOND
                handleInput()

                // Update the current state
                stateManager.update(deltaTime)

                render()

                previousTime = startTime

            }

            sync(startTime)
        }
    }

    private fun sync(startTime : Long) {

        while (System.nanoTime() < startTime + nanoSecondsPerFrame) {
            Thread.sleep(1)
        }

    }

    private fun handleInput() {
        glfwPollEvents()
        stateManager.handleInput()
    }

    private fun render() {
        // Clear the screen
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT)

        // Render the current state
        stateManager.render()

        // Swap the buffers
        glfwSwapBuffers(window)
    }

    private fun setGlfwConfig() {
        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE)

        // Set up an error callback
        GLFWErrorCallback.createPrint(System.err).set()
    }

    private fun setCallbacks() {
        glfwSetFramebufferSizeCallback(window) { window: Long, width: Int, height: Int ->
            this.width = width
            this.height = height
            this.resize = true
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(
            window
        ) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(
                window,
                true
            ) // We will detect this in the rendering loop
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
        glfwSetErrorCallback(null)?.free();
    }

    fun setClearColor(r : Float, g : Float, b : Float, a : Float) {
        glClearColor(r, g, b, a)
    }
    fun isKeyPressed(keyCode : Int) : Boolean {
        return glfwGetKey(window, keyCode) == GLFW_PRESS
    }
    fun windowShouldClose() : Boolean {
        return glfwWindowShouldClose(window)
    }



}