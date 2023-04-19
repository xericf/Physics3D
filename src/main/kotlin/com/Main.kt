package com

import com.Engine.GameEngine


// https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter02/chapter2.html
fun main() {

    val gameEngine = GameEngine(title = "3D Physics Engine")
    gameEngine.run()

}
