package com

import com.GUI.GameEngine
import com.GUI.Window
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryUtil.NULL


// https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter02/chapter2.html
fun main() {

    val gameEngine = GameEngine(title = "3D Physics Engine")
    gameEngine.run()

}
