package com.Engine

import kotlin.system.measureNanoTime
object Time {
    private var startTime: Long = System.nanoTime()
    private var lastFrameTime: Long = System.nanoTime()

    val time: Float
        get() = (System.nanoTime() - startTime) / 1000000000f

    val deltaTime: Float
        get() {
            val currentTime = System.nanoTime()
            val delta = (currentTime - lastFrameTime) / 1000000000f
            lastFrameTime = currentTime
            return delta
        }

    fun update() {
        val currentTime = System.currentTimeMillis()
        val deltaTime = (currentTime - lastFrameTime) / 1000f
        lastFrameTime = currentTime
        // Update other time-related values or objects here.
    }
}
