package com.State

abstract class State {
    abstract fun init()
    abstract fun update(dt : Float)
    abstract fun handleInput()
    abstract fun render()
    abstract fun dispose()

}