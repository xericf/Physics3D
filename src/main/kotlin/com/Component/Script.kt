package com.Component

import com.Engine.GameObject

open class Script(gameObject: GameObject) : Component(gameObject) {
    open fun start() { }
    open fun update() { }

}