package com.GUI

import com.State.StateManager
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryUtil
import kotlin.properties.Delegates

class Window(private val width : Int, private val height : Int, private var title : String) {
    private var windowHandle by Delegates.notNull<Long>()
    private val stateManager : StateManager = StateManager()

    fun run() {
        init()
        loop()
        cleanup()
    }

    private fun init() {

        // Initialize GLFW and create a windowHandle
        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)

        windowHandle = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
        if (windowHandle == MemoryUtil.NULL) {
            throw RuntimeException("Failed to create GLFW windowHandle")
        }

        // Set up an error callback
        GLFWErrorCallback.createPrint(System.err).set()

        // Center the winodw
        val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())!!
        GLFW.glfwSetWindowPos(
            windowHandle,
            (vidmode.width() - width) / 2,
            (vidmode.height() - height) / 2
        )

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(windowHandle)
        // Enable VSync
        GLFW.glfwSwapInterval(1)
        // Make the windowHandle visible
        GLFW.glfwShowWindow(windowHandle)



    }


    private fun loop() {
        while (!GLFW.glfwWindowShouldClose(windowHandle)) {
            GLFW.glfwSwapBuffers(windowHandle)
            GLFW.glfwPollEvents()
        }
    }

    private fun cleanup() {
        // Clean up
        GLFW.glfwDestroyWindow(windowHandle)
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)?.free()
    }

}