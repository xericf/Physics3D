package com.Engine

import com.Component.Renderer
import com.GUI.Window
import com.State.State
import com.State.StateManager
import com.State.WorldState

class GameEngine(width: Int = 1200, height: Int = 720, title: String, private val initialState : State = WorldState()) {


    private val window : Window = Window(width, height, title)
    private val stateManager : StateManager = StateManager(window)

    // Time
    val NANOSECOND : Float = 0.000000001f
    val BILLION : Float = 1_000_000_000f
    val secondsPerFrame : Float = 1f / 60f
    val nanoSecondsPerFrame : Float = secondsPerFrame / BILLION

    private lateinit var renderer : Renderer

    private fun init() {
        window.init()
        // Push the initial state onto the state manager
        stateManager.pushState(initialState)
    }

    fun run() {
        try {
            init()
            loop()
        } catch (e : Exception) {
            e.printStackTrace()
        } finally {
            cleanup()
        }
    }

    private fun loop() {

        var startTime: Long
        var previousTime = System.nanoTime()
        var deltaTime: Float

        while (!window.windowShouldClose()) {

            startTime = System.nanoTime()
            deltaTime = (startTime - previousTime) / BILLION

            // we will only update the physics / world at a fixed interval
            if (deltaTime >= secondsPerFrame) {

                deltaTime = (startTime - previousTime) / BILLION
                input()

                update(deltaTime)

                render()

                previousTime = startTime

            }

            sync(startTime)
        }
    }

    private fun update(deltaTime : Float) {
        // Update the current state
        stateManager.update(deltaTime)
    }
    private fun input() {
        window.input() // polls new input
        stateManager.handleInput() // uses the new inputs
    }
    private fun render() {
        window.clear()

        // Render the current state
        stateManager.render()

        window.update() // swaps buffers, must be last


    }
    //        stateManager.render()

    private fun sync(startTime : Long) {

        while (System.nanoTime() < startTime + nanoSecondsPerFrame) {
            Thread.sleep(1)
        }

    }
    private fun cleanup() {
        // Clean up resources and dispose of the current states
        stateManager.dispose()
        Loader.cleanup()
        window.cleanup()

    }




}