package com

import com.GUI.Window
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryUtil.NULL

fun main() {

    val window = Window(width = 1280, height = 720, title = "3D Physics Engine")
    window.run()

}
