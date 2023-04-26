package com.GUI

import com.Engine.Renderer
import com.State.WorldState
import com.State.StateManager
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil
import kotlin.properties.Delegates


class Window(private var width : Int, private var height : Int, title : String) {
    private var title : String = title
        set(value) {
            title = value
            glfwSetWindowTitle(window, title)
        }


    private var window by Delegates.notNull<Long>()

    private var resize : Boolean = false
    private var vSync : Boolean = true


    fun init() {

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

        // Set up double buffering
        glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);


        // Make the window visible
        glfwShowWindow(window)

        GL.createCapabilities()
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_STENCIL_TEST)
        glEnable(GL_CULL_FACE)
        glEnable(GL_BACK) // cull backside of objects we can't see

    }

    fun clear() {
        // Clear the screen
        setClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        glClear(GL11.GL_COLOR_BUFFER_BIT)
    }

    fun update() {
        // Swap the buffers
        glfwSwapBuffers(window)
    }

    fun input() {
        glfwPollEvents()
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

    fun cleanup() {
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