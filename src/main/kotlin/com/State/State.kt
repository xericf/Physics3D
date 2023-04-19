package com.State

import com.GUI.Window

abstract class State {
    abstract fun init()
    abstract fun update(dt : Float)
    abstract fun handleInput(window : Window)
    abstract fun render(window : Window)
    abstract fun dispose()

}