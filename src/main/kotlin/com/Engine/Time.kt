package com.Engine

import kotlin.system.measureNanoTime

// Saving this time implementation for objects in the World, the update will be called in the world (?)
// and then the scripts will call this object.
object Time {
    private var startTime: Long = System.nanoTime()
    private var lastFrameTime: Long = System.nanoTime()

    var time: Float = 0f
        private set
    var deltaTime: Float = 0f
        private set



    fun update() {
        var currentTime : Long = System.nanoTime()

        deltaTime = (currentTime - lastFrameTime) / 1000000000f
        time = currentTime / 1000000000f

        lastFrameTime = currentTime

    }
}
